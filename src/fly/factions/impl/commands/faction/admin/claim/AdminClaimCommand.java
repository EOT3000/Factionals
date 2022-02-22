package fly.factions.impl.commands.faction.admin.claim;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.model.Faction;
import fly.factions.api.model.Plot;
import fly.factions.api.model.User;
import fly.factions.impl.model.PlotImpl;
import fly.factions.impl.util.Plots;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class AdminClaimCommand extends CommandDivision {
    public AdminClaimCommand() {
        addSubCommand("*", this);
        addSubCommand("", this);

        addHelpEntry("/f admin claim <faction> <type>", "ADMIN ONLY");
    }

    public boolean run(CommandSender sender, String faction, String type) {
        User user = USERS.get(((Player) sender).getUniqueId());

        if(sender.hasPermission("factions.admin") && user.isAdminMode()) {
            if(type.equalsIgnoreCase("unclaim")) {
                Plot plot = PLOTS.get(Plots.getLocationId(((Player) sender).getLocation()));

                plot.setFaction(null);

                sender.sendMessage("done unclaim");

            } else {
                Faction f = FACTIONS.get(faction);

                Plot plot = PLOTS.get(Plots.getLocationId(((Player) sender).getLocation()));

                if(plot != null) {
                    plot.setFaction(f);
                } else {
                    Chunk c = ((Player) sender).getChunk();

                    Plot plot1 = new PlotImpl(c.getX(), c.getZ(), c.getWorld(), f);
                }

                sender.sendMessage("done claim");

            }

            return true;
        }

        sender.sendMessage("no");

        return false;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[]{
                ArgumentType.STRING,
                ArgumentType.STRING
        };
    }
}
