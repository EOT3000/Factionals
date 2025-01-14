package me.fly.factions.impl.serialization;

import me.fly.factions.Factionals;
import me.fly.factions.api.model.User;
import me.fly.factions.api.serialization.Serializer;
import me.fly.factions.impl.model.UserImpl;
import me.fly.factions.impl.util.Pair;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class UserSerializer extends Serializer<User> {
    private File dir = new File("plugins\\Factionals\\users");

    public UserSerializer(Factionals factionals) {
        super(User.class, factionals);
    }

    @Override
    public File dir() {
        return dir;
    }

    @Override
    public User load(File file) {
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        User user = new UserImpl(UUID.fromString(configuration.getString("uuid")));

        user.setPower(configuration.getInt("power"));

        return user;
    }

    @Override
    public Pair<YamlConfiguration, File> save(User savable) {
        YamlConfiguration config = new YamlConfiguration();

        config.set("uuid", savable.getUniqueId().toString());
        config.set("power", savable.getPower());

        return new Pair<>(config, new File(dir.getPath() + "\\" + savable.getUniqueId().toString()));
    }
}
