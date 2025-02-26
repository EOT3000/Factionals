package me.fly.factions.impl.commands.faction.autoClaim;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.model.User;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutoClaimCommand extends CommandDivision {
    public AutoClaimCommand() {
        addHelpEntry("/f autoClaim", "Toggle whether you are auto claiming");

        addSubCommand("*", this);
        addSubCommand("", this);
    }

    public boolean run(CommandSender sender) {
        User user = USERS.get(((Player) sender).getUniqueId());

        user.sendMessage(ChatColor.RED + "DEPRECATED. USE /f claim auto");

        //user.setAutoClaiming(!user.getAutoClaiming());

        //user.sendMessage(user.getAutoClaiming() ? ChatColor.LIGHT_PURPLE + "Now AutoClaiming" : ChatColor.LIGHT_PURPLE + "No longer AutoClaiming");

        return true;
    }
}
