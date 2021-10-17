package fly.factions.impl.commands.faction.list;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.model.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ListCommand extends CommandDivision {
    public ListCommand() {
        addSubCommand("", this);
    }

    public boolean run(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "List:");

        for(Faction faction : FACTIONS.list()) {
            sender.sendMessage(faction.getName());
        }

        return true;
    }
}