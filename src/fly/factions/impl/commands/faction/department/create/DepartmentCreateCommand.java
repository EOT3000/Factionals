package fly.factions.impl.commands.faction.department.create;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.User;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.impl.model.ExecutiveDivisionImpl;
import fly.factions.impl.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class DepartmentCreateCommand extends CommandDivision {
    public DepartmentCreateCommand() {
        addHelpEntry("/f department create <name>", "Create a department with the given name");


        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String name) {
        User user = USERS.get(Bukkit.getPlayer(sender.getName()).getUniqueId());

        if(!nameGood(name)) {
            user.sendMessage(ChatColor.RED + "ERROR: invalid characters. Use numbers, english letters, or underscores in your department name (3-24 characters)");

            return false;
        }

        if(user.getFaction().getDepartment(name) != null) {
            user.sendMessage(ChatColor.RED + "ERROR: the department" + ChatColor.YELLOW + name + ChatColor.RED + " already exists");
            return false;
        }

        user.getFaction().addDepartment(new ExecutiveDivisionImpl(name, user, user.getFaction()));

        user.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully created department " + ChatColor.YELLOW + name);
        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.STRING
        };
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
