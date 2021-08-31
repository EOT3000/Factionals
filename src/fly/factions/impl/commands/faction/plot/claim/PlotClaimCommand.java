package fly.factions.impl.commands.faction.plot.claim;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.model.Lot;
import fly.factions.api.model.Plot;
import fly.factions.api.model.User;
import fly.factions.impl.util.Plots;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlotClaimCommand extends CommandDivision {
    public PlotClaimCommand() {
        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender) {
        Location location = ((Player) sender).getLocation();

        User user = USERS.get(((Player) sender).getUniqueId());

        Plot plot = PLOTS.get(Plots.getLocationId(location));

        if(plot == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: you are not standing in a lot");

            return false;
        }

        Lot lot = plot.getLot(location);

        if (lot == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: you are not standing in a lot");

            return false;
        }

        if(lot.getPrice() >= 0) {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully bought lot for " + ChatColor.YELLOW + lot.getPrice());

            lot.getOwner().addToBalance(lot.getPrice());
            lot.setOwner(user);
            lot.setPrice(-1);

            return true;
        }

        sender.sendMessage(ChatColor.RED + "ERROR: lot is not for sale");
        return false;
    }
}
