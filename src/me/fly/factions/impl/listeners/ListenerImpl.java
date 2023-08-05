package me.fly.factions.impl.listeners;

import me.fly.factions.Factionals;
import me.fly.factions.api.model.User;
import me.fly.factions.api.registries.Registry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.UUID;

public class ListenerImpl implements Listener {
    public ListenerImpl() {
        Bukkit.getPluginManager().registerEvents(this, Factionals.getFactionals());
    }

    protected User getUserFromPlayer(Player player) {
        return ((Registry<User, UUID>) Factionals.getFactionals().getRegistry(User.class)).get(player.getUniqueId());
    }

    protected User addUser(User user) {
        ((Registry<User, UUID>) Factionals.getFactionals().getRegistry(User.class)).set(user.getUniqueId(), user);

        return user;
    }
}
