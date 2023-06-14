package hellfall.zeroconfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A holder for all the annotations used for NullConfig
 */
@SuppressWarnings("unused")
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {

    /**
     * Specifies the file name/path of the file to save the config to.
     * This is required for your config to be read.
     * <p>
     * The config will be saved as css, so the file extension <code>.css</code> is recommended.
     * </p>
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface File {
        String value();
    }

    /**
     * Specifies the default category name.
     * Optional, defaults to "default"
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface DefaultCategory {
        String value();
    }

    /**
     * Specifies the default boolean styling.
     * Can be any value in {@link hellfall.zeroconfig.ZeroConfig.BoolStyle}
     * Optional, defaults to TRUE_FALSE
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface DefaultBoolStyle {
        ZeroConfig.BoolStyle value();
    }

    /**
     * Specifies a comment to put at the top of the config file.
     * This should not be formatted as a comment, just as plain text.
     * Optional, defaults to none
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Header {
        String value();
    }

    /**
     * Specifies the key to be used for the value.
     * This is required for the value to be saved/loaded.
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Key {
        String value();
    }

    /**
     * Specifies the comment for the value.
     * This should not be formatted as a comment, just as plain text.
     * Optional, defaults to none
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Comment {
        String value();
    }

    /**
     * Specifies the category for the value.
     * Optional, defaults to the default category
     * @see DefaultCategory
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Category {
        String value();
    }

    /**
     * Specifies the boolean styling for the value.
     * Can be any value in {@link hellfall.zeroconfig.ZeroConfig.BoolStyle}
     * Optional, defaults to the default styling.
     * @see DefaultBoolStyle
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface BoolStyle {
        ZeroConfig.BoolStyle value();
    }
}
