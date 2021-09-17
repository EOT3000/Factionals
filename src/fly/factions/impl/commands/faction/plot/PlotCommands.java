package fly.factions.impl.commands.faction.plot;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.plot.map.PlotMapCommand;
import fly.factions.impl.commands.faction.plot.set.PlotSetCommand;

public class PlotCommands extends CommandDivision {
    public PlotCommands() {
        addHelpEntry("/f plot set", "See plot and lot settings commands");
        addHelpEntry("/f plot map", "See plot lots");

        addSubCommand("set", new PlotSetCommand());
        addSubCommand("map", new PlotMapCommand());
    }
}
