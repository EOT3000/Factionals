package me.fly.factions.impl.commands.faction.plot.set.type;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Lot;
import me.fly.factions.api.model.PlotType;
import me.fly.factions.api.model.Region;
import me.fly.factions.api.model.User;
import me.fly.factions.impl.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlotSetTypeCommand extends CommandDivision {
    public PlotSetTypeCommand() {
        addHelpEntry("/f plot set type <region> <plot id> <type>", "Change the lot type of the plot");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String region, String lotId, String type) {
        User user = USERS.get(((Player) sender).getUniqueId());
        Region regionObject = user.getFaction().getRegion(region);

        Lot lot = regionObject.getLots().get(Integer.parseInt(lotId));

        if(lot != null) {
            lot.setType(PlotType.valueOf(type.toUpperCase()));

            return true;
        }

        sender.sendMessage(ChatColor.RED + "ERROR: that lot isn't real");

        return false;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.STRING,
                ArgumentType.INT,
                ArgumentType.PLOT_TYPE
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
