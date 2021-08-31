package fly.factions.impl.commands.faction.info;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.model.Faction;
import fly.factions.api.model.Region;
import org.bukkit.command.CommandSender;

public class InfoCommand extends CommandDivision {
    public InfoCommand() {
        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String faction) {
        Faction factionObject = FACTIONS.get(faction);

        sender.sendMessage("Name:" + factionObject.getName());
        sender.sendMessage("Leader: " + factionObject.getLeader().getName());
        sender.sendMessage("Members: " + factionObject.getMembers());
        sender.sendMessage("Plots: " + factionObject.getPlots().size());

        sender.sendMessage("");

        for(Region region : factionObject.getRegions()) {
            sender.sendMessage(region.getName());
            sender.sendMessage("Leader: " + region.getLeader().getName());
            sender.sendMessage("Members: " + region.getMembers());
            sender.sendMessage("Lots: " + region.getLots().keySet());

        }

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[]{
                ArgumentType.FACTION
        };
    }
}
