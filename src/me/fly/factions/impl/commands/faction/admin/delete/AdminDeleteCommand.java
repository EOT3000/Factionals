package me.fly.factions.impl.commands.faction.admin.delete;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminDeleteCommand extends CommandDivision {
    public AdminDeleteCommand() {
        addSubCommand("*", this);
        addSubCommand("", this);

        addHelpEntry("/f admin delete <faction>", "ADMIN ONLY");
    }

    public boolean run(CommandSender sender, String faction, String type) {
        User user = USERS.get(((Player) sender).getUniqueId());

        if(sender.hasPermission("factions.admin") && user.isAdminMode()) {
            Faction f = FACTIONS.get(faction);

            f.delete();

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
