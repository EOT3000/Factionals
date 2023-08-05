package me.fly.factions.impl.commands.faction.faction;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.impl.commands.faction.faction.join.FactionJoinCommand;

public class FactionCommand extends CommandDivision {
    public FactionCommand() {
        addSubCommand("join", new FactionJoinCommand());

        addHelpEntry("/f faction join <faction>", "Merge your faction into another one");
    }
}
