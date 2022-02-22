package fly.factions.impl.commands.faction.admin.mode;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.model.Faction;
import fly.factions.api.model.Plot;
import fly.factions.api.model.User;
import fly.factions.impl.model.PlotImpl;
import fly.factions.impl.util.Plots;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminModeCommand extends CommandDivision {
    public AdminModeCommand() {
        addSubCommand("*", this);
        addSubCommand("", this);

        addHelpEntry("/f admin mode", "ADMIN ONLY");
    }

    public boolean run(CommandSender sender) {
        User user = USERS.get(((Player) sender).getUniqueId());

        if(sender.hasPermission("factions.admin")) {
            user.setAdminMode(!user.isAdminMode());

            user.sendMessage("" + user.isAdminMode());

            return true;
        }

        return false;
    }
}
