package me.fly.factions.impl.commands.faction.home;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.Region;
import me.fly.factions.api.model.User;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand extends CommandDivision {
    public HomeCommand() {
        addSubCommand("*", this);
        addSubCommand("", this);
    }

    public boolean run(CommandSender sender) {
        User user = USERS.get(((Player) sender).getUniqueId());

        if(user.getFaction() != null && user.getFaction().getHome() != null) {

        }

        return true;
    }
}
