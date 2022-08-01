package fly.factions.impl.util;

import fly.factions.Factionals;
import fly.factions.api.model.Faction;
import fly.factions.api.model.User;
import fly.factions.api.registries.Registry;
import org.bukkit.Bukkit;

import java.util.UUID;

public class Ticker {
    private static Registry<Faction, String> factionRegistry;
    private static Registry<User, UUID> userRegistry;

    static {
        factionRegistry = Factionals.getFactionals().getRegistry(Faction.class, String.class);
        userRegistry = Factionals.getFactionals().getRegistry(User.class, UUID.class);
    }

    public static void tick() {
        for(User user : userRegistry.list()) {
            if(Bukkit.getOfflinePlayer(user.getUniqueId()).isOnline()) {
                user.setPower(user.getPower() + 10);
            } else {
                user.setPower(user.getPower() + 1);
            }
        }
    }
}
