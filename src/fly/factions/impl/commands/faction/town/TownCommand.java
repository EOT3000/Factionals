package fly.factions.impl.commands.faction.town;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.town.create.TownCreateCommand;

public class TownCommand extends CommandDivision {
    public TownCommand() {
        addSubCommand("create", new TownCreateCommand());

        addHelpEntry("/f town create <region> <name>", "Create a town in that region");
    }
}
