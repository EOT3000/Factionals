package fly.factions.impl.commands.faction.set;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.set.format.SetFormatCommand;

public class SetCommand extends CommandDivision {
    public SetCommand() {
        addHelpEntry("/f set format", "See dynmap format commands");

        addSubCommand("format", new SetFormatCommand());
    }
}
