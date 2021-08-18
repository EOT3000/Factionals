package fly.factions.api.commands;

import fly.factions.Factionals;
import fly.factions.api.model.Faction;
import fly.factions.api.model.User;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.api.registries.Registry;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public enum CommandRequirement {
    REQUIRE_PLAYER {
        @Override
        public boolean has(CommandSender sender, Object info) {
            return sender instanceof Player;
        }

        @Override
        public String format(CommandSender sender) {
            return ChatColor.RED + "ERROR: you must be a player to run this command";
        }
    },
    REQUIRE_MEMBER_FACTION {
        @Override
        public boolean has(CommandSender sender, Object info) {
            return USERS.get(((Player) sender).getUniqueId()).getFaction() != null;
        }

        @Override
        public String format(CommandSender sender) {
            return ChatColor.RED + "ERROR: you must be in a faction to run this command";
        }
    },
    REQUIRE_USER_PERMISSION {
        @Override
        public boolean has(CommandSender sender, Object info) {
            return getUser(sender).getFaction().hasPermission(getUser(sender), (FactionPermission) info);
        }

        @Override
        public String format(CommandSender sender) {
            return ChatColor.RED + "ERROR: no permission";
        }
    },
    REQUIRE_REGION_LEADER {
        @Override
        public boolean has(CommandSender sender, Object info) {
            return getUser(sender).getFaction().getRegion((String) info).getLeader().equals(getUser(sender));
        }

        @Override
        public String format(CommandSender sender) {
            return ChatColor.RED + "ERROR: no permission";
        }
    }, 
    REQUIRE_DEPARTMENT_LEADER {
        @Override
        public boolean has(CommandSender sender, Object info) {
            return getUser(sender).getFaction().getRegion((String) info).getLeader().equals(getUser(sender));
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

    public abstract boolean has(CommandSender sender, Object info);

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
