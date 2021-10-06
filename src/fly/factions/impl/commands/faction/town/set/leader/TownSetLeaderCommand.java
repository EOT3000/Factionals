package fly.factions.impl.commands.faction.town.set.leader;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.Faction;
import fly.factions.api.model.Region;
import fly.factions.api.model.Town;
import fly.factions.impl.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TownSetLeaderCommand extends CommandDivision {
    public TownSetLeaderCommand() {
        addHelpEntry("/f set leader <faction> <region> <town> <user name>", "Set the town's leader");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String faction, String region, String town, String user) {
        Faction factionObject = FACTIONS.get(faction);
        Region regionObject = factionObject.getRegion(region);

        if(regionObject == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the region " + ChatColor.YELLOW + region + " does not exist");

            return false;
        }

        Town townObject = regionObject.getTown(town);

        if(townObject == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the town " + ChatColor.YELLOW + town + " does not exist");

            return false;
        }

        townObject.setLeader(USERS.get(Bukkit.getPlayer(user).getUniqueId()));

        sender.sendMessage(ChatColor.GREEN + "Successfully set the town leader");

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        //TODO: region and town types

        return new ArgumentType[] {
                ArgumentType.FACTION,
                ArgumentType.STRING,
                ArgumentType.STRING,
                ArgumentType.USER
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_REGION_LEADER, 1)
        };
    }
}
