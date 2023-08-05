package me.fly.factions.impl.commands.faction.plot;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.impl.commands.faction.plot.info.PlotInfoCommand;
import me.fly.factions.impl.commands.faction.plot.map.PlotMapCommand;
import me.fly.factions.impl.commands.faction.plot.sell.PlotSellCommand;
import me.fly.factions.impl.commands.faction.plot.set.PlotSetCommand;

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
