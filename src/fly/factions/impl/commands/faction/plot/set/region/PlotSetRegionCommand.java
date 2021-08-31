package fly.factions.impl.commands.faction.plot.set.region;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.*;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.impl.util.Pair;
import fly.factions.impl.util.Plots;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlotSetRegionCommand extends CommandDivision {
    public PlotSetRegionCommand() {
        addHelpEntry("/f plot set region <region>", "Set a plot's region");


        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String region) {
        Location location = ((Player) sender).getLocation();

        User user = USERS.get(((Player) sender).getUniqueId());

        Plot plot = PLOTS.get(Plots.getLocationId(location));

        if(plot == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: you are not standing in a plot");

            return false;
        }

        Region factionRegion = user.getFaction().getRegion(region);

        if(factionRegion == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the region " + ChatColor.YELLOW + region + ChatColor.RED + " does not exist");

            return false;
        }

        plot.setAdministrator(factionRegion);

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully set plot region");

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
