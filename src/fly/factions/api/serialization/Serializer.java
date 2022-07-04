package fly.factions.api.serialization;

import fly.factions.Factionals;
import fly.factions.api.model.Savable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

public abstract class Serializer<T extends Savable> {
    protected Factionals factionals;

    public Serializer(Class<T> clazz, Factionals factionals) {
        this.factionals = factionals;
        Factionals.getFactionals().getRegistry(Serializer.class, Class.class).set(clazz, this);
    }

    public static void saveAll(Collection<? extends Savable> list, Class clazz) {
        for(Savable savable : list) {
            save0(savable, clazz);
        }
    }

    public static <X extends Savable> Collection<X> loadAll(Class<X> clazz) {
        Serializer<X> serializer = Factionals.getFactionals().getRegistry(Serializer.class, Class.class).get(clazz);
        List<X> list = new ArrayList<>();

        serializer.dir().mkdirs();

        for(File file : serializer.dir().listFiles()) {
            if(!file.isDirectory()) {
                try {
                    X x = serializer.load(file);

                    if (x == null) {
                        continue;
                    }

                    list.add(x);
                } catch (Exception e) {
                    Factionals.getFactionals().getLogger().log(Level.SEVERE, "Error on loading file " + file.getName());

                    e.printStackTrace();
                }
            }
        }

        serializer.onLoad();

        return list;
    }

    private static <X extends Savable> void save0(X savable, Class clazz) {
        Serializer<X> serializer = Factionals.getFactionals().getRegistry(Serializer.class, Class.class).get(clazz);

        serializer.save(savable);
    }

    public void onLoad() {

    }

    public abstract File dir();

    public abstract void save(T t);

    public abstract T load(File file);
}
