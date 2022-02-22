package fly.factions.impl.commands.faction.set;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.set.description.SetDescriptionCommand;
import fly.factions.impl.commands.faction.set.format.SetFormatCommand;
import fly.factions.impl.commands.faction.set.open.SetOpenCommand;

public class SetCommand extends CommandDivision {
    public SetCommand() {
        addHelpEntry("/f set format", "See dynmap format commands");
        addHelpEntry("/f set description <desc>", "Set your faction's description");
        addHelpEntry("/f set open <true | false>", "Set the faction to open if true, or closed if false");

        addSubCommand("format", new SetFormatCommand());
        addSubCommand("description", new SetDescriptionCommand());
        addSubCommand("open", new SetOpenCommand());
    }
}
