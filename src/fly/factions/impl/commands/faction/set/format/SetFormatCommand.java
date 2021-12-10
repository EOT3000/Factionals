package fly.factions.impl.commands.faction.set.format;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.set.format.border.SetFormatBorderCommand;
import fly.factions.impl.commands.faction.set.format.fill.SetFormatFillCommand;

public class SetFormatCommand extends CommandDivision {
    public SetFormatCommand() {
        addHelpEntry("/f set format fill <fillRed> <fillGreen> <fillBlue> <fillOpacity>", "Set the faction dynmap fill format");

        addHelpEntry("/f set format border <borderRed> <borderGreen> <borderBlue>", "Set the faction dynmap border color");


        addSubCommand("fill", new SetFormatFillCommand());

        addSubCommand("border", new SetFormatBorderCommand());
    }
}
