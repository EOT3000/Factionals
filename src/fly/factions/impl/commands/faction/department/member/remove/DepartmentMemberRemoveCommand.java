package fly.factions.impl.commands.faction.department.member.remove;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.ExecutiveDivision;
import fly.factions.api.model.Faction;
import fly.factions.api.model.User;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.impl.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DepartmentMemberRemoveCommand extends CommandDivision {
    public boolean run(CommandSender sender, String victim, String department) {
        Faction faction = USERS.get(((Player) sender).getUniqueId()).getFaction();

        if(faction.getDepartment(department) == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the department " + ChatColor.YELLOW + department + ChatColor.RED + " does not exist");

            return false;
        }

        User victimUser = USERS.get(Bukkit.getOfflinePlayer(victim).getUniqueId());
        ExecutiveDivision division = faction.getDepartment(department);

        if(!division.getMembers().contains(victimUser)) {
            sender.sendMessage(ChatColor.RED + "ERROR: " + ChatColor.YELLOW + victim + ChatColor.RED + " is not in this group");

            return false;
        }

        division.removeMember(victimUser);
        sender.sendMessage(ChatColor.GREEN + "Successfully removed user");

        return false;
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.INTERNAL_MANAGEMENT)
        };
    }
}
