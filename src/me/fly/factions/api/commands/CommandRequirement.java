package me.fly.factions.api.commands;

import me.fly.factions.Factionals;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.Region;
import me.fly.factions.api.model.User;
import me.fly.factions.api.permissions.FactionPermission;
import me.fly.factions.api.registries.Registry;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public enum CommandRequirement {
    REQUIRE_PLAYER {
        @Override
        public boolean has0(CommandSender sender, Object info) {
            return sender instanceof Player;
        }

        @Override
        public String format(CommandSender sender) {
            return ChatColor.RED + "ERROR: you must be a player to run this command";
        }
    },
    REQUIRE_MEMBER_FACTION {
        @Override
        public boolean has0(CommandSender sender, Object info) {
            return USERS.get(((Player) sender).getUniqueId()).getFaction() != null;
        }

        @Override
        public String format(CommandSender sender) {
            return ChatColor.RED + "ERROR: you must be in a faction to run this command";
        }
    },
    REQUIRE_USER_PERMISSION {
        @Override
        public boolean has(CommandSender sender, Object... info) {
            return getUser(sender).getFaction().hasPermission(getUser(sender), (FactionPermission) info[0]) || getUser(sender).isAdminMode();
        }

        @Override
        public String format(CommandSender sender) {
            return ChatColor.RED + "ERROR: no permission";
        }
    },
    REQUIRE_REGION_LEADER {
        @Override
        public boolean has0(CommandSender sender, Object info) {
            User user = getUser(sender);
            Region region = user.getFaction().getRegion((String) info);

            if(region == null) {
                return false;
            }

            return region.getLeader().equals(user) || user.getFaction().hasPermission(user, FactionPermission.OWNER);
        }

        @Override
        public String format(CommandSender sender) {
            return ChatColor.RED + "ERROR: no permission or this region does not exist";
        }
    }, 
    REQUIRE_DEPARTMENT_LEADER {
        @Override
        public boolean has0(CommandSender sender, Object info) {
            return getUser(sender).getFaction().getDepartment((String) info).getLeader().equals(getUser(sender)) || getUser(sender).getFaction().hasPermission(getUser(sender), FactionPermission.OWNER);
        }

        @Override
        public String format(CommandSender sender) {
            return ChatColor.RED + "ERROR: no permission";
        }
    };

    private static Factionals API = Factionals.getFactionals();
    private static Registry<User, UUID> USERS = API.getRegistry(User.class, UUID.class);
    private static Registry<Faction, String> FACTIONS = API.getRegistry(Faction.class, String.class);

    private static User getUser(CommandSender sender) {
        return USERS.get(((Player) sender).getUniqueId());
    }

    public boolean has0(CommandSender sender, Object info) {
        return true;
    }

    public boolean has(CommandSender sender, Object... info) {
        return has0(sender, info[0]);
    }

    public abstract String format(CommandSender sender);

    public static boolean checkAll(CommandSender sender, Object[] objects, CommandRequirement... commandRequirements) {
        int count = 0;

        for(Object object : objects) {
            if(!commandRequirements[count].has(sender, object)) {
                sender.sendMessage(commandRequirements[count].format(sender));

                return false;
            }

            count++;
        }

        return true;
    }
}
