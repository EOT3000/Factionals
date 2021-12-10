package fly.factions.impl.commands.faction.plot;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.plot.info.PlotInfoCommand;
import fly.factions.impl.commands.faction.plot.map.PlotMapCommand;
import fly.factions.impl.commands.faction.plot.sell.PlotSellCommand;
import fly.factions.impl.commands.faction.plot.set.PlotSetCommand;

public class PlotCommands extends CommandDivision {
    public PlotCommands() {
        addHelpEntry("/f plot set", "See plot and lot settings commands");
        addHelpEntry("/f plot map", "See plot lots");
        addHelpEntry("/f plot info", "See plot information");
        addHelpEntry("/f plot sell <lot id> <price | nfs | notforsale>", "Set the plot's price");

        addSubCommand("set", new PlotSetCommand());
        addSubCommand("map", new PlotMapCommand());
        addSubCommand("sell", new PlotSellCommand());
        addSubCommand("info", new PlotInfoCommand());
    }
}
