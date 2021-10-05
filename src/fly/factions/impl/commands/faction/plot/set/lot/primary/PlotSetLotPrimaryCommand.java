package fly.factions.impl.commands.faction.plot.set.lot.primary;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.impl.commands.faction.plot.set.lot.PlotSetLotCommand;
import fly.factions.impl.util.Pair;

import org.bukkit.command.CommandSender;

public class PlotSetLotPrimaryCommand extends CommandDivision {
    public PlotSetLotPrimaryCommand() {
        addHelpEntry("/f plot set lot primary <x1> <z1> <x2> <z2> <region> <lot id>", "Set the lot's primary area (must be at least 35 blocks)");


        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String x1s, String z1s, String x2s, String z2s, String region, String ids) {
        return PlotSetLotCommand.doSet(sender, x1s, z1s, x2s, z2s, region, ids, 35, 1);
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
