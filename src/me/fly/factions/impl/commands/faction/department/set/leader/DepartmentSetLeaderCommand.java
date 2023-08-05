package me.fly.factions.impl.commands.faction.department.set.leader;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.ExecutiveDivision;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.User;
import me.fly.factions.api.permissions.FactionPermission;
import me.fly.factions.impl.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class DepartmentSetLeaderCommand extends CommandDivision {
    public DepartmentSetLeaderCommand() {
        addHelpEntry("/f department set leader <department> <user>", "Set the department leader");


        addSubCommand("*", this);
    }

    @SuppressWarnings({"Pain please tell me what the NPE supression is", "unused", "deprecation"})
    public boolean run(CommandSender sender, String division, String newUser) {
        User user = USERS.get(Bukkit.getPlayer(sender.getName()).getUniqueId());
        Faction faction = user.getFaction();
        ExecutiveDivision divisionr = faction.getDepartment(division);

        User victim = USERS.get(Bukkit.getOfflinePlayer(newUser).getUniqueId());

        if (divisionr == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the department " + ChatColor.YELLOW + division + ChatColor.LIGHT_PURPLE + " does not exist");

            return false;
        }

        if (!victim.getFaction().equals(faction)) {
            sender.sendMessage(ChatColor.RED + "ERROR: this user is not in your faction");

            return false;
        }

        divisionr.setLeader(victim);

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully set department leader to " + ChatColor.YELLOW + newUser);

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.STRING,
                ArgumentType.USER
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.OWNER)
        };
    }
}
