package me.fly.factions.impl.commands.faction.player;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.model.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PlayerCommand extends CommandDivision {
    public PlayerCommand() {
        addSubCommand("*", this);

        addHelpEntry("/f player <player name>", "View information about a player");
    }

    public boolean run(CommandSender sender, String player) {
        User userObject = USERS.get(Bukkit.getOfflinePlayer(player).getUniqueId());

        sender.sendMessage(ChatColor.GOLD + userObject.getName());
        if(userObject.getFaction() != null) {
            sender.sendMessage(ChatColor.DARK_AQUA + "Faction: " + ChatColor.WHITE + userObject.getFaction().getName());
        }
        sender.sendMessage(ChatColor.DARK_AQUA + "Power: " + ChatColor.WHITE + userObject.getPower());

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[]{
                ArgumentType.USER
        };
    }
}
