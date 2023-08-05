package me.fly.factions.impl.commands.faction.listOpen;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.model.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ListOpenCommand extends CommandDivision {
    public ListOpenCommand() {
        addSubCommand("", this);
    }

    public boolean run(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Open Factions List:");

        for(Faction faction : FACTIONS.list()) {
            if(faction.isOpen()) {
                sender.sendMessage(faction.getName());
            }
        }

        return true;
    }
}
