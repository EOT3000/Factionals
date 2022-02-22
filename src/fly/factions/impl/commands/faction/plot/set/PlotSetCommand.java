package fly.factions.impl.commands.faction.plot.set;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.plot.set.lot.PlotSetLotCommand;
import fly.factions.impl.commands.faction.plot.set.permission.PlotSetPermissionCommand;
import fly.factions.impl.commands.faction.plot.set.region.PlotSetRegionCommand;
import fly.factions.impl.commands.faction.plot.set.town.PlotSetTownCommand;
import fly.factions.impl.commands.faction.plot.set.type.PlotSetTypeCommand;

public class PlotSetCommand extends CommandDivision {
    public PlotSetCommand() {
        addHelpEntry("/f plot set permission <lot id> <permission> <permissible> <on|off>", "Set the lot's permission for some entity (uses region currently standing in)");
        addHelpEntry("/f plot set region <region>", "Set a plot's region");
        addHelpEntry("/f plot set town <lot id> <region> <town>", "Set the lot's town");
        addHelpEntry("/f plot set lot <x1> <z1> <x2> <z2> <region> <lot id>", "Set an area to a lot");
        addHelpEntry("/f plot set type <region> <plot id> <type>", "Change the lot type of the plot");

        addSubCommand("permission", new PlotSetPermissionCommand());
        addSubCommand("region", new PlotSetRegionCommand());
        addSubCommand("town", new PlotSetTownCommand());
        addSubCommand("lot", new PlotSetLotCommand());
        addSubCommand("type", new PlotSetTypeCommand());
    }
}
