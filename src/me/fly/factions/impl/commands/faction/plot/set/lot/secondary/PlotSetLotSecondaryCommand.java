package me.fly.factions.impl.commands.faction.plot.set.lot.secondary;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.impl.commands.faction.plot.set.lot.PlotSetLotCommand;
import me.fly.factions.impl.util.Pair;

import org.bukkit.command.CommandSender;

public class PlotSetLotSecondaryCommand extends CommandDivision {
    public PlotSetLotSecondaryCommand() {
        addHelpEntry("/f plot set lot secondary <x1> <z1> <x2> <z2> <region> <lot id>", "Set the lot's secondary area (must be at least 21 blocks)");


        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String x1s, String z1s, String x2s, String z2s, String region, String ids) {
        return PlotSetLotCommand.doSet(sender, x1s, z1s, x2s, z2s, region, ids, 21, 2);
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.INT,
                ArgumentType.INT,
                ArgumentType.INT,
                ArgumentType.INT,
                ArgumentType.STRING,
                ArgumentType.STRING,
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_REGION_LEADER, 4)
        };
    }
}
