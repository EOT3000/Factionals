package fly.factions.impl.commands.faction.kick;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.ExecutiveDivision;
import fly.factions.api.model.Region;
import fly.factions.api.model.Town;
import fly.factions.api.model.User;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.impl.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand extends CommandDivision {
    public KickCommand() {
        addHelpEntry("/f kick <user>", "Kicks a user from the faction");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String user) {
        User victim = USERS.get(Bukkit.getPlayerUniqueId(user));
        User kicker = USERS.get(((Player) sender).getUniqueId());

        if(victim.getFaction() != kicker.getFaction()) {
            sender.sendMessage(ChatColor.RED + "ERROR: you do not share a faction");

            return false;
        }

        if(getLevel(victim, true) < getLevel(kicker, false)) {
            victim.setFaction(null);

            kicker.getFaction().broadcast(ChatColor.LIGHT_PURPLE + "User " + ChatColor.YELLOW + victim.getName() + ChatColor.LIGHT_PURPLE + " was kicked");

            victim.sendMessage(ChatColor.LIGHT_PURPLE + "You have been kicked from your faction");

            return true;
        } else {
            kicker.sendMessage(ChatColor.RED + "ERROR: no permission");
        }

        return false;
    }

    private int getLevel(User victim, boolean isVictim) {
        if(victim.getFaction().getLeader().equals(victim)) {
            return 4;
        }

        if(victim.getFaction().hasPermission(victim, FactionPermission.OWNER)) {
            return 3;
        }

        if(isVictim) {
            for(ExecutiveDivision department : victim.getFaction().getDepartments()) {
                if(department.getLeader().equals(victim)) {
                    return 2;
                }
            }

            for (Region region : victim.getFaction().getRegions()) {
                if(region.getLeader().equals(victim)) {
                    return 2;
                }

                for(Town town : region.getTowns()) {
                    if(town.getLeader().equals(victim)) {
                        return 2;
                    }
                }
            }
        } else {
            return 1;
        }

        return 0;

    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.USER
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.USER_KICK)
        };
    }
}
