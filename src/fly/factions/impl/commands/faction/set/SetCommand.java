package fly.factions.impl.commands.faction.set;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.set.description.SetDescriptionCommand;
import fly.factions.impl.commands.faction.set.format.SetFormatCommand;

public class SetCommand extends CommandDivision {
    public SetCommand() {
        addHelpEntry("/f set format", "See dynmap format commands");
        addHelpEntry("/f set description <desc>", "Set your faction's description");

        addSubCommand("format", new SetFormatCommand());
        addSubCommand("description", new SetDescriptionCommand());
    }
}
