package me.fly.factions.impl.commands.faction.plot.set.town;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Lot;
import me.fly.factions.api.model.Region;
import me.fly.factions.api.model.Town;
import me.fly.factions.api.model.User;
import me.fly.factions.impl.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlotSetTownCommand extends CommandDivision {
    public PlotSetTownCommand() {
        addHelpEntry("/f plot set region <lot id> <region> <town>", "Set the lot's town");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, int id, String region, String town) {
        User user = USERS.get(((Player) sender).getUniqueId());

        Region factionRegion = user.getFaction().getRegion(region);

        if(factionRegion == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the region " + ChatColor.YELLOW + region + " does not exist");

            return false;
        }

        Town factionTown = factionRegion.getTown(town);

        if(factionTown == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the town " + ChatColor.YELLOW + town + " does not exist");

            return false;
        }

        Lot lot = factionRegion.getLots().get(id);

        if(lot == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the lot " + ChatColor.YELLOW + id + " does not exist");

            return false;
        }

        lot.setTown(factionTown);

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully added lot " + ChatColor.YELLOW + id + ChatColor.LIGHT_PURPLE + " to town " + ChatColor.YELLOW);

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.INT,
                ArgumentType.STRING,
                ArgumentType.STRING
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_REGION_LEADER, 0)
        };
    }
}
