package hellfall.zeroconfig;

import nilloader.api.NilLogger;
import nilloader.api.lib.qdcss.BadValueException;
import nilloader.api.lib.qdcss.QDCSS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SuppressWarnings("unused")
public class ZeroConfig {

    private static final NilLogger log = NilLogger.get("libzero|config");

    /**
     * Synchronizes a config.
     * <p>
     * Reads values from the config file (if it exists), then writes the config to disk.
     * <p>
     * This means that the config file will always stay in sync with the fields of the config class.
     * @param cfg The class to synchronize.
     */
    @SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
    public static void sync(Class<?> cfg) {
        Config.File cfgFileName = cfg.getAnnotation(Config.File.class);
        if (cfgFileName != null) {
            String defaultCategory = "default";
            Config.DefaultCategory defaultCategoryAnno = cfg.getAnnotation(Config.DefaultCategory.class);
            if (defaultCategoryAnno != null) {
                defaultCategory = defaultCategoryAnno.value();
            }
            BoolStyle defaultBoolStyle = BoolStyle.TRUE_FALSE;
            Config.DefaultBoolStyle defaultBoolStyleAnno = cfg.getAnnotation(Config.DefaultBoolStyle.class);
            if (defaultBoolStyleAnno != null) {
                defaultBoolStyle = defaultBoolStyleAnno.value();
            }
            boolean appendDefaults = false;
            Config.Comment.AppendDefault appendDefaultsAnno = cfg.getAnnotation(Config.Comment.AppendDefault.class);
            if (appendDefaultsAnno != null) {
                appendDefaults = appendDefaultsAnno.value();
            }

            File cfgFile = new File(cfgFileName.value());
            log.debug("Loading {} from {}", cfgFile, cfg.getName());
            QDCSS css = QDCSS.load("", "");
            try {
                css = QDCSS.load(cfgFile);
            } catch (FileNotFoundException ignored) {
            } catch (IOException e) {
                log.error("Failed to load {}!", cfgFileName.value());
            }

            List<ConfigEntry> entries = new ArrayList<>();

            try {
                for (Field f : cfg.getDeclaredFields()) {
                    Config.Key keyAnno = f.getAnnotation(Config.Key.class);
                    if (keyAnno != null) {
                        String k = keyAnno.value();
                        String category = defaultCategory;
                        Config.Category categoryAnno = f.getAnnotation(Config.Category.class);
                        if (categoryAnno != null) {
                            category = categoryAnno.value();
                        }

                        BoolStyle boolStyle = defaultBoolStyle;
                        Config.BoolStyle boolStyleAnno = f.getAnnotation(Config.BoolStyle.class);
                        if (boolStyleAnno != null) {
                            boolStyle = boolStyleAnno.value();
                        }

                        String comment = null;
                        Config.Comment commentAnno = f.getAnnotation(Config.Comment.class);
                        if (commentAnno != null) {
                            comment = commentAnno.value();
                        }

                        Config.Comment.AppendDefault appendDefaultFieldAnno = f.getAnnotation(Config.Comment.AppendDefault.class);
                        if ((appendDefaultFieldAnno != null && appendDefaultFieldAnno.value()) || (appendDefaultFieldAnno == null && appendDefaults)) {
                            String defaultValue = f.getType() == boolean.class ? boolStyle.getString(f.getBoolean(null)) : f.get(null).toString();
                            if (comment == null) {
                                comment = "default: " + defaultValue;
                            }
                            else {
                                comment = comment.concat("\ndefault: " + defaultValue);
                            }
                        }

                        String value = null;
                        if (f.getType() == String.class) {
                            Optional<String> opt;
                            try {
                                opt = css.get(category + "." + k);
                            } catch (BadValueException e) {
                                log.warn("Found malformed key {}", category + "." + k);
                                opt = Optional.empty();
                            }
                            String v;
                            if (opt.isPresent()) {
                                v = opt.get();
                                f.set(null, v);
                            }
                            else {
                                v = (String) f.get(null);
                            }
                            value = '"' + v + '"';
                        } else if (f.getType() == boolean.class) {
                            Optional<Boolean> opt;
                            try {
                                opt = css.getBoolean(category + "." + k);
                            } catch (BadValueException e) {
                                log.warn("Found malformed key {}", category + "." + k);
                                opt = Optional.empty();
                            }
                            boolean v;
                            if (opt.isPresent()) {
                                v = opt.get();
                                f.set(null, v);
                            }
                            else {
                                v = f.getBoolean(null);
                            }
                            value = boolStyle.getString(v);
                        } else if (f.getType() == int.class) {
                            Optional<Integer> opt;
                            try {
                                opt = css.getInt(category + "." + k);
                            } catch (BadValueException e) {
                                log.warn("Found malformed key {}", category + "." + k);
                                opt = Optional.empty();
                            }
                            int v;
                            if (opt.isPresent()) {
                                v = opt.get();
                                f.set(null, v);
                            }
                            else {
                                v = f.getInt(null);
                            }
                            value = String.valueOf(v);
                        } else if (f.getType() == double.class) {
                            Optional<Double> opt;
                            try {
                                opt = css.getDouble(category + "." + k);
                            } catch (BadValueException e) {
                                log.warn("Found malformed key {}", category + "." + k);
                                opt = Optional.empty();
                            }
                            double v;
                            if (opt.isPresent()) {
                                v = opt.get();
                                f.set(null, v);
                            }
                            else {
                                v = f.getDouble(null);
                            }
                            value = String.valueOf(v);
                        } else if (f.getType().isEnum()) {
                            // warning: much bullshit here
                            @SuppressWarnings("rawtypes")
                            Class eType = f.getType();
                            Optional<Object> opt;
                            try {
                                opt = css.getEnum(category + "." + k, eType);
                            } catch (BadValueException e) {
                                log.warn("Found malformed key {}", category + "." + k);
                                opt = Optional.empty();
                            }
                            Object v;
                            if (opt.isPresent()) {
                                v = opt.get();
                                f.set(null, v);
                            }
                            else {
                                v = f.get(null);
                            }
                            value = ((Enum<?>) v).name().toLowerCase(Locale.ROOT);
                        }

                        entries.add(new ConfigEntry(k, comment, value, category));
                    } else {
                        log.debug("Ignoring field {} as it does not declare a key", f.getName());
                    }
                }
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }

            entries.sort(Comparator.comparing(a -> a.category));

            StringBuilder out = new StringBuilder();
            Config.Header header = cfg.getAnnotation(Config.Header.class);
            if (header != null) {
                out.append(makeComment(header.value(), "")).append("\n");
            }
            String currentCategory = null;
            for (ConfigEntry entry : entries) {
                if (!entry.category.equals(currentCategory)) {
                    if (currentCategory != null) {
                        out.deleteCharAt(out.length() - 1);
                        out.append("}\n\n");
                    }
                    currentCategory = entry.category;
                    out.append(currentCategory).append(" {\n");
                }
                if (entry.comment != null) {
                    out.append(makeComment(entry.comment, "\t"));
                }
                out.append("\t")
                        .append(entry.key)
                        .append(": ")
                        .append(entry.value)
                        .append(";\n\n");
            }
            out.deleteCharAt(out.length() - 1);
            out.append("}\n");
            if (cfgFile.getParentFile() != null) {
                cfgFile.getParentFile().mkdirs();
            }
            try (FileOutputStream fos = new FileOutputStream(cfgFile)) {
				fos.write(out.toString().getBytes(StandardCharsets.UTF_8));
			} catch (IOException e) {
				e.printStackTrace();
				log.error("Failed to save config {}!", cfgFileName.value());
			}

        }
        else {
            log.warn("Class {} did not specify a file name! Ignoring", cfg.getName());
        }
    }

    /**
     * Makes sure all static values are correct in the current class loader
     * <p>
     * Call this from your config's static initializer
     * <p>
     * Can also be called on anything else to migrate <code>String</code>, <code>boolean</code>, <code>int</code>, <code>double</code>, and <code>Enum</code> values
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void crossClassloader(Class<?> cfg) {
        if (cfg.getClassLoader() != ClassLoader.getSystemClassLoader()) {
            log.debug("Copying static values from " + cfg.getSimpleName() + " to a new classloader");
            try {
                Class<?> original = Class.forName(cfg.getName(), true, ClassLoader.getSystemClassLoader());
                for (Field f : original.getDeclaredFields()) {
                    if (Modifier.isStatic(f.getModifiers())) {
                        Field current = cfg.getDeclaredField(f.getName());
                        if (f.getType() == String.class || f.getType() == boolean.class || f.getType() == int.class || f.getType() == double.class) {
                            current.set(null, f.get(null));
                        }
                        else if (f.getType().isEnum()) {
                            current.set(null, Enum.valueOf((Class)current.getType(), ((Enum<?>)f.get(null)).name()));
                        }
                        else {
                            log.warn("Copying type " + f.getType().getSimpleName() + " is not implemented!");
                        }
                    }
                }
            } catch (Throwable t) {
                throw new AssertionError(t);
            }
        }
    }

    private static String makeComment(String in, String prefix) {
        return prefix + "/*\n" + prefix + " * " +
                in.replace("\n", "\n" + prefix + " * ")
                + "\n" + prefix + "*/\n";
    }

    private static class ConfigEntry {
        String key;
        String comment;
        String value;
        String category;
        ConfigEntry(String key, String comment, String value, String category) {
            this.key = key;
            this.comment = comment;
            this.value = value;
            this.category = category;
        }
    }

    /**
     * An enum representing if booleans should be written as true/false or on/off
     */
    public enum BoolStyle {
        TRUE_FALSE,
        ON_OFF;

        public String getString(boolean b) {
            switch (this) {
                case TRUE_FALSE:
                    return b ? "true" : "false";
                case ON_OFF:
                    return b ? "on" : "off";
                default:
                    return "null";
            }
        }
    }
}
