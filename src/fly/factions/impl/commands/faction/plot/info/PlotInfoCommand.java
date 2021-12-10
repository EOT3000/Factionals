package fly.factions.impl.commands.faction.plot.info;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.model.Lot;
import fly.factions.api.model.Permissible;
import fly.factions.api.model.Plot;
import fly.factions.api.model.Region;
import fly.factions.api.permissions.PlotPermission;
import fly.factions.impl.util.Plots;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlotInfoCommand extends CommandDivision {
    public PlotInfoCommand() {
        addSubCommand("*", this);
        addSubCommand("", this);

        addHelpEntry("/f plot info", "See plot and lot information");
    }

    public boolean run(CommandSender sender) {
        Player player = (Player) sender;

        Plot plot = PLOTS.get(Plots.getLocationId(player.getLocation()));

        if(plot == null) {
            sender.sendMessage("ERROR: not in a claimed chunk");

            return false;
        }

        sender.sendMessage("Faction: " + plot.getFaction());
        sender.sendMessage("Administrator: " + plot.getAdministrator());

        sender.sendMessage("");

        if(plot.getAdministrator() instanceof Region) {
            Lot lot = ((Region) plot.getAdministrator()).getLot(player.getLocation());

            if(lot == null) {
                return false;
            }

            sender.sendMessage("Lot ID: " + lot.getId());
            sender.sendMessage("Lot owner: " + lot.getOwner().getFormattedName());
            sender.sendMessage("Permissions: ");

            for(PlotPermission permission : lot.getPermissions().keySet()) {
                String send = permission.name() + ": ";

                for(Permissible permissible : lot.getPermissions().get(permission)) {
                    send+=permissible.getFormattedName() + "; ";
                }

                sender.sendMessage(ChatColor.GRAY + "-----");

                sender.sendMessage(send);
            }

            return true;
        }

        return false;
    }
}
