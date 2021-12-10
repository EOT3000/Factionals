package fly.factions.impl.commands.faction.faction;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.faction.join.FactionJoinCommand;

public class FactionCommand extends CommandDivision {
    public FactionCommand() {
        addSubCommand("join", new FactionJoinCommand());

        addHelpEntry("/f faction join <faction>", "Merge your faction into another one");
    }
}
