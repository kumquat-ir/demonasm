package hellfall.demonasm;

import nilloader.api.NilMetadata;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// this class should not be necessary but forge decided a second classloader was a good idea
// warning: cursed code
public class ForgeModListHacks {
    public static ArrayList<String> ids = new ArrayList<>();
    public static ArrayList<String> names = new ArrayList<>();
    public static ArrayList<String> descs = new ArrayList<>();
    public static ArrayList<String> versions = new ArrayList<>();
    public static ArrayList<String> authors = new ArrayList<>();

    public static void parseFrom(List<NilMetadata> metas) {
        for (NilMetadata meta : metas) {
            ids.add(meta.id);
            names.add(meta.name);
            descs.add(meta.description);
            versions.add(meta.version);
            authors.add(meta.authors);
        }
    }

    // used to cast between classloaders via serialization/deserialization
    @SuppressWarnings("unchecked")
    private static <T> T castObj(Object o) throws IOException, ClassNotFoundException {
        if (o != null) {
            ByteArrayOutputStream baous = new ByteArrayOutputStream();
            {
                try (ObjectOutputStream oos = new ObjectOutputStream(baous)) {
                    oos.writeObject(o);
                }
            }

            byte[] bb = baous.toByteArray();
            if (bb.length > 0) {
                ByteArrayInputStream bais = new ByteArrayInputStream(bb);
                ObjectInputStream ois = new ObjectInputStream(bais);
                return (T) ois.readObject();
            }
        }
        return null;
    }

    static {
        // if this is being loaded on another classloader, get all the required data from the original instantiation
        Class<?> me = ForgeModListHacks.class;
        if (me.getClassLoader() != ClassLoader.getSystemClassLoader()) {
            try {
                Class<?> real = Class.forName(ForgeModListHacks.class.getName(), true, ClassLoader.getSystemClassLoader());
                ids = castObj(real.getDeclaredField("ids").get(null));
                names = castObj(real.getDeclaredField("names").get(null));
                descs = castObj(real.getDeclaredField("descs").get(null));
                versions = castObj(real.getDeclaredField("versions").get(null));
                authors = castObj(real.getDeclaredField("authors").get(null));
            } catch (Throwable t) {
                throw new AssertionError(t);
            }
        }
    }
}
