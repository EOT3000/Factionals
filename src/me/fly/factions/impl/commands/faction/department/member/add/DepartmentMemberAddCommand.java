package me.fly.factions.impl.commands.faction.department.member.add;

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
import org.bukkit.entity.Player;

public class DepartmentMemberAddCommand extends CommandDivision {
    public DepartmentMemberAddCommand() {
        addHelpEntry("/f department member add <user> <department>", "Add a user to the department");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String victim, String department) {
        Faction faction = USERS.get(((Player) sender).getUniqueId()).getFaction();

        if(faction.getDepartment(department) == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the department " + ChatColor.YELLOW + department + ChatColor.RED + " does not exist");

            return false;
        }

        User victimUser = USERS.get(Bukkit.getOfflinePlayer(victim).getUniqueId());
        ExecutiveDivision division = faction.getDepartment(department);

        User manager = USERS.get(((Player) sender).getUniqueId());

        if(!(division.getLeader().equals(manager) || division.getFaction().hasPermission(manager, FactionPermission.INTERNAL_MANAGEMENT))) {
            sender.sendMessage(ChatColor.RED + "ERROR: no permission");

            return false;
        }

        if(division.getMembers().contains(victimUser)) {
            sender.sendMessage(ChatColor.RED + "ERROR: " + ChatColor.YELLOW + victim + ChatColor.RED + " is already in this group");

            return false;
        }

        division.addMember(victimUser);
        sender.sendMessage(ChatColor.GREEN + "Successfully added user");

        return false;
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null)
        };
    }
}
