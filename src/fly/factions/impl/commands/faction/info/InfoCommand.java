package fly.factions.impl.commands.faction.info;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.model.Faction;
import fly.factions.api.model.Region;
import fly.factions.api.model.User;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class InfoCommand extends CommandDivision {
    public InfoCommand() {
        addSubCommand("*", this);

        addHelpEntry("/f info <faction name>", "View information about a faction");
    }

    public boolean run(CommandSender sender, String faction) {
        Faction factionObject = FACTIONS.get(faction);

        sender.sendMessage(ChatColor.GOLD + faction);
        sender.sendMessage(ChatColor.DARK_AQUA + "Leader: " + ChatColor.WHITE + factionObject.getLeader().getName());

        String members = "Leader " + factionObject.getLeader().getName();

        for(User user : factionObject.getMembers()) {
            if(!user.equals(factionObject.getLeader())) {
                members += (", " + user.getName());
            }
        }

        String regions = "";

        for(Region region : factionObject.getRegions()) {
            regions += (", " + region.getName());
        }

        regions = regions.replaceFirst(", ", "");

        //I gave up here lmfao
        sender.sendMessage(translate("&3Members &b[&3" + factionObject.getMembers().size() + "&b]&3: &r" + members));
        sender.sendMessage(translate("&3Provinces &b[&3" + factionObject.getRegions().size() + "&b]&3: &r" + regions));
        sender.sendMessage(translate("&3Chunks&r/&3Power&r/&3Max Power&r: " + factionObject.getPlots().size() + "&7/&f" + factionObject.getCurrentPower() + "&7/&f" + factionObject.getMaxPower()));

        sender.sendMessage(translate("&3Description&r: " + factionObject.getDescription()));

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[]{
                ArgumentType.FACTION
        };
    }
}
