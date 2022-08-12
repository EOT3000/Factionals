package fly.factions.impl.commands.faction.admin.transfer;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.model.LandAdministrator;
import fly.factions.api.model.Permissible;
import fly.factions.api.model.Plot;
import fly.factions.api.model.User;
import fly.factions.api.permissions.Permissibles;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AdminTransferCommand extends CommandDivision {
    public AdminTransferCommand() {
        addSubCommand("*", this);
        addSubCommand("", this);
    }

    public boolean run(CommandSender sender, String in, String out) {
        User admin = USERS.get(((Player) sender).getUniqueId());

        if(sender.hasPermission("factions.admin") && admin.isAdminMode()) {
            List<Permissible> a = Permissibles.get(in);
            List<Permissible> b = Permissibles.get(out);

            if(a.size() != 1) {
                sender.sendMessage("in improper count " + a.size());

                return false;
            }

            if(b.size() != 1) {
                sender.sendMessage("out improper count" + b.size());

                return false;
            }

            Permissible a1 = a.get(0);
            Permissible b1 = b.get(0);

            if(a1 instanceof LandAdministrator) {
                if(b1 instanceof LandAdministrator) {
                    LandAdministrator<Plot> adminIn = (LandAdministrator<Plot>) a1;
                    LandAdministrator<Plot> adminOut = (LandAdministrator<Plot>) b1;

                    for(Plot plot : adminIn.getPlots()) {
                        plot.setFaction(adminOut.getFaction());

                        plot.setAdministrator(adminOut);
                    }

                    sender.sendMessage("done");

                    return true;
                } else {
                    sender.sendMessage(b1 + " is wrong type (out)");
                }
            } else {
                sender.sendMessage(a1 + " is wrong type (in)");
            }
        }

        return false;
    }
}
