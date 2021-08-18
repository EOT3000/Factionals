package fly.factions.impl.commands.faction.plot.set;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.plot.set.lot.PlotSetLotCommand;
import fly.factions.impl.commands.faction.plot.set.permission.PlotSetPermissionCommand;
import fly.factions.impl.commands.faction.plot.set.region.PlotSetRegionCommand;
import fly.factions.impl.commands.faction.plot.set.town.PlotSetTownCommand;

public class PlotSetCommand extends CommandDivision {
    public PlotSetCommand() {
        addHelpEntry("/f plot set permission <lot id> <permission> <permissible> <on|off>", "Set the lot's permission for some entity (uses region currently standing in)");

        addSubCommand("permission", new PlotSetPermissionCommand());
        addSubCommand("region", new PlotSetRegionCommand());
        addSubCommand("town", new PlotSetTownCommand());
        addSubCommand("lot", new PlotSetLotCommand());
    }
}
