package fly.factions.impl.commands.faction.province.set.format;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.province.set.format.border.ProvinceSetFormatBorderCommand;
import fly.factions.impl.commands.faction.province.set.format.fill.ProvinceSetFormatFillCommand;

public class ProvinceSetFormatCommand extends CommandDivision {
    public ProvinceSetFormatCommand() {
        addHelpEntry("/f province set format fill <province name> <fillRed> <fillGreen> <fillBlue> <fillOpacity>", "Set the province dynmap fill format");

        addHelpEntry("/f province set format border <province name> <borderRed> <borderGreen> <borderBlue>", "Set the province dynmap border color");


        addSubCommand("fill", new ProvinceSetFormatFillCommand());

        addSubCommand("border", new ProvinceSetFormatBorderCommand());
    }
}
