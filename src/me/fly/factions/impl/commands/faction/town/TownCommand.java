package me.fly.factions.impl.commands.faction.town;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.impl.commands.faction.town.create.TownCreateCommand;
import me.fly.factions.impl.commands.faction.town.set.TownSetCommand;

public class TownCommand extends CommandDivision {
    public TownCommand() {
        addSubCommand("create", new TownCreateCommand());
        addSubCommand("set", new TownSetCommand());

        addHelpEntry("/f town create <region> <name>", "Create a town in that region");
        addHelpEntry("/f town set", "View town settings commands");
    }
}
