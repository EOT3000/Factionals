package me.fly.factions.impl.commands.faction.province.info;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.Region;
import me.fly.factions.api.model.Town;
import me.fly.factions.api.model.User;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ProvinceInfoCommand extends CommandDivision {
    public ProvinceInfoCommand() {
        addSubCommand("*", this);

        addHelpEntry("/f province info <faction name> <province name>", "View information about a province");
    }

    public boolean run(CommandSender sender, String faction, String regionString) {
        Faction factionObject = FACTIONS.get(faction);

        Region region = factionObject.getRegion(regionString);

        if(region == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the province " + ChatColor.YELLOW + regionString + ChatColor.RED + " does not exist");

            return false;
        }

        sender.sendMessage("");

        sender.sendMessage(ChatColor.GOLD + regionString);
        sender.sendMessage(ChatColor.DARK_AQUA + "Leader: " + ChatColor.WHITE + region.getLeader().getName());

        String members = "";

        for(User user : region.getMembers()) {
            members += (", " + user.getName());
        }

        members = members.replaceFirst(", ", "");

        String towns = "";

        for(Town town : region.getTowns()) {
            towns += (", " + town.getName());
        }

        towns = towns.replaceFirst(", ", "");

        //I game up here lmfao
        sender.sendMessage(translate("&3Members &b[&3" + region.getMembers().size() + "&b]&3: &r" + members));
        sender.sendMessage(translate("&3Towns &b[&3" + region.getTowns().size() + "&b]&3: &r" + towns));
        sender.sendMessage(translate("&3Chunks&r: " + region.getPlots().size()));

        sender.sendMessage(translate("&3Lots&r:" + (region.getLots().size())));

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[]{
                ArgumentType.FACTION,
                ArgumentType.STRING
        };
    }
}
