package me.fly.factions.impl.commands.faction.plot.sell;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Lot;
import me.fly.factions.api.model.Plot;
import me.fly.factions.api.model.Region;
import me.fly.factions.impl.util.Pair;
import me.fly.factions.impl.util.Plots;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class
PlotSellCommand extends CommandDivision {
    public PlotSellCommand() {
        addHelpEntry("/f plot sell <lot id> <price | nfs | notforsale>", "Set the plot's price");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String lot, String price) {
        int lotId = Integer.parseInt(lot);
        Plot plot = PLOTS.get(Plots.getLocationId(((Player) sender).getLocation()));

        if(plot == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: you are not standing in a plot");

            return false;
        }

        if(plot.getAdministrator() instanceof Region) {
            Lot lotObject = ((Region) plot.getAdministrator()).getLots().get(lotId);

            if(lotObject == null) {
                sender.sendMessage(ChatColor.RED + "ERROR: no such lot");

                return false;
            }

            if(lotObject.getOwner().userHasPlotPermissions(USERS.get(((Player) sender).getUniqueId()), true, false)) {
                try {
                    if (price.equalsIgnoreCase("nfs") || price.equalsIgnoreCase("notforsale")) {
                        lotObject.setPrice(-1);

                        sender.sendMessage(ChatColor.GREEN + "Successfully set the plot to not for sale");
                    } else {
                        lotObject.setPrice(Math.abs(Integer.parseInt(price)));
                        sender.sendMessage(ChatColor.GREEN + "Successfully set the lot price to " + ChatColor.YELLOW + Math.abs(Integer.parseInt(price)));
                    }

                    return true;
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "ERROR: " + ChatColor.YELLOW + "price " + ChatColor.RED + "must be either a number, 'nfs' or 'notforsale'");

                    return false;
                }
            }

            sender.sendMessage(ChatColor.RED + "ERROR: you are not the lot owner");
        }

        return false;
    }


    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.INT,
                ArgumentType.STRING,
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
        };
    }
}
