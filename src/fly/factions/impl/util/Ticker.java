package fly.factions.impl.util;

import fly.factions.Factionals;
import fly.factions.api.model.ExecutiveDivision;
import fly.factions.api.model.Faction;
import fly.factions.api.model.Region;
import fly.factions.api.model.User;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.api.registries.Registry;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    public static void tickHour() {
        for (User user : userRegistry.list()) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(user.getUniqueId());

            long l = player.getLastSeen();

            if(user.getFaction() != null && user.getFaction().getLeader().equals(user)) {
                if(System.currentTimeMillis() - l >= 21 * 24 * 60 * 60 * 1000) {
                    Faction faction = user.getFaction();

                    if(faction.getMembers().size() == 1) {
                        faction.delete();

                        continue;
                    }

                    if(faction.getMembers().size() == 2) {
                        Set<User> nl = faction.getMembers();

                        nl.remove(user);

                        faction.setLeader(nl.iterator().next());

                        user.setFaction(null);

                        continue;
                    }

                    User replacement = replaceFirst(faction, user);

                    if(replacement == null) {
                        faction.delete();
                    } else {
                        faction.setLeader(user);

                        user.setFaction(null);
                    }
                }
            } else {
                if (System.currentTimeMillis() - l >= 14 * 24 * 60 * 60 * 1000) {
                    if (user.getFaction() != null) {
                        user.getFaction().broadcast(ChatColor.YELLOW + user.getName() + ChatColor.LIGHT_PURPLE + " was kicked for inactivity");

                        user.setFaction(null);
                    }
                }
            }
        }
    }

    private static User replaceFirst(Faction faction, User leader) {
        for(ExecutiveDivision division : faction.getDepartments()) {
            if(division.canDo(FactionPermission.OWNER)) {
                if(!division.getLeader().equals(leader)) {
                    return division.getLeader();
                }
            }
        }

        return replaceSecond(faction, leader);
    }

    private static User replaceSecond(Faction faction, User leader) {
        for(ExecutiveDivision division : faction.getDepartments()) {
            if(division.canDo(FactionPermission.OWNER)) {
                for(User m : division.getMembers()) {
                    if (!m.equals(leader)) {
                        return m;
                    }
                }
            }
        }

        return replaceThird(faction, leader);
    }

    private static User replaceThird(Faction faction, User leader) {
        for (ExecutiveDivision division : faction.getDepartments()) {
            if (!division.getLeader().equals(leader)) {
                return division.getLeader();
            }
        }

        return replaceFourth(faction, leader);
    }

    private static User replaceFourth(Faction faction, User leader) {
        for (Region region : faction.getRegions()) {
            if (!region.getLeader().equals(leader)) {
                return region.getLeader();
            }
        }

        return replaceFifth(faction, leader);
    }

    private static User replaceFifth(Faction faction, User leader) {
        for(ExecutiveDivision division : faction.getDepartments()) {
            for (User m : division.getMembers()) {
                if (!m.equals(leader)) {
                    return m;
                }
            }
        }

        return replaceSixth(faction, leader);
    }

    private static User replaceSixth(Faction faction, User leader) {
        User lu = null;
        long lt = 0;

        for(User member : faction.getMembers()) {
            if(!member.equals(leader)) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(member.getUniqueId());

                if(player.getLastSeen() > lt) {
                    lt = player.getLastSeen();

                    lu = member;
                }
            }
        }

        return lu;
    }

}
