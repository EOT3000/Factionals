package me.fly.factions.impl.commands.faction.province.delete;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.User;
import me.fly.factions.api.permissions.FactionPermission;
import me.fly.factions.impl.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ProvinceDeleteCommand extends CommandDivision {
    public ProvinceDeleteCommand() {
        addHelpEntry("/f province delete <name>", "Deletes given province");


        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String name) {
        User user = USERS.get(Bukkit.getPlayer(sender.getName()).getUniqueId());

        if (user.getFaction().getRegion(name) == null) {
            user.sendMessage(ChatColor.RED + "ERROR: the province " + ChatColor.YELLOW + name + ChatColor.RED + " does not exist");
            return false;
        }

        user.getFaction().removeRegion(user.getFaction().getRegion(name));

        user.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully deleted province " + ChatColor.YELLOW + name);

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
