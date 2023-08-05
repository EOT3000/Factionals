package me.fly.factions.impl.commands.faction.leave;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.model.User;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand extends CommandDivision {
    public LeaveCommand() {
        addSubCommand("*", this);
        addSubCommand("", this);
    }

    public boolean run(CommandSender sender) {
        User user = USERS.get(((Player) sender).getUniqueId());

        if(user.getFaction() != null) {
            user.getFaction().broadcast(ChatColor.LIGHT_PURPLE + "User " + ChatColor.YELLOW + user.getName() + ChatColor.LIGHT_PURPLE + " has left the faction");

            user.setFaction(null);

            return true;
        }

        return false;
    }
}
