package fly.factions.impl.commands.faction.region.set.format;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.region.set.format.border.SetRegionBorderFormatCommand;
import fly.factions.impl.commands.faction.region.set.format.fill.SetRegionFillFormatCommand;

public class RegionSetFormatCommand extends CommandDivision {
    public RegionSetFormatCommand() {
        addHelpEntry("/f region set format fill <region name> <fillRed> <fillGreen> <fillBlue> <fillOpacity>", "Set the region dynmap fill format");

        addHelpEntry("/f region set format border <region name> <borderRed> <borderGreen> <borderBlue>", "Set the region dynmap border color");


        addSubCommand("fill", new SetRegionFillFormatCommand());

        addSubCommand("border", new SetRegionBorderFormatCommand());
    }
}