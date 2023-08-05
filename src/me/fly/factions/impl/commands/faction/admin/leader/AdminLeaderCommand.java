package me.fly.factions.impl.commands.faction.admin.leader;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminLeaderCommand extends CommandDivision {
    public AdminLeaderCommand() {
        addSubCommand("*", this);
        addSubCommand("", this);

        addHelpEntry("/f admin leader <faction> <user>", "ADMIN ONLY");
    }

    public boolean run(CommandSender sender, String faction, String user) {
        User admin = USERS.get(((Player) sender).getUniqueId());

        User leader = USERS.get(Bukkit.getOfflinePlayer(user).getUniqueId());
        Faction factionObject = FACTIONS.get(faction.toLowerCase());

        if(sender.hasPermission("factions.admin") && admin.isAdminMode()) {
            factionObject.setLeader(leader);

            sender.sendMessage("done");

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
