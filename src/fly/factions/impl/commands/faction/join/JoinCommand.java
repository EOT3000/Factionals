package fly.factions.impl.commands.faction.join;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.Faction;
import fly.factions.api.model.User;
import fly.factions.impl.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class JoinCommand extends CommandDivision {
    public JoinCommand() {
        addHelpEntry("/f join <faction>", "Attempt to join the given faction");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String faction) {
        User user = USERS.get(((Player) sender).getUniqueId());

        Faction factionObject = FACTIONS.get(faction);

        if(user.getInvites().contains(factionObject) || factionObject.isOpen()) {
            user.setFaction(factionObject);

            user.getFaction().broadcast(ChatColor.YELLOW + user.getName() + ChatColor.LIGHT_PURPLE + " has joined the faction");

            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "ERROR: you do not have an invite to this faction, and it is not open");
        }

        return false;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[]{
                ArgumentType.FACTION
        };
    }
}
