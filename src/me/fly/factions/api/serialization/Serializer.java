package me.fly.factions.api.serialization;

import me.fly.factions.Factionals;
import me.fly.factions.api.model.Savable;
import me.fly.factions.impl.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

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
        List<Pair<YamlConfiguration, File>> configs = new ArrayList<>();

        for(Savable savable : list) {
            configs.add(save0(savable, clazz));
        }

        Bukkit.getScheduler().runTaskAsynchronously(Factionals.getFactionals(), () -> {
            for(Pair<YamlConfiguration, File> pair : configs) {
                try {
                    pair.getKey().save(pair.getValue());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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

    private static <X extends Savable> Pair<YamlConfiguration, File> save0(X savable, Class clazz) {
        Serializer<X> serializer = Factionals.getFactionals().getRegistry(Serializer.class, Class.class).get(clazz);

        return serializer.save(savable);
    }

    public void onLoad() {

    }

    public abstract File dir();

    public abstract Pair<YamlConfiguration, File> save(T t);

    public abstract T load(File file);
}
