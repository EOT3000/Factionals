package me.fly.factions.impl.commands.faction.town.set;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.impl.commands.faction.town.set.leader.TownSetLeaderCommand;

public class TownSetCommand extends CommandDivision {
    public TownSetCommand() {
        addSubCommand("leader", new TownSetLeaderCommand());

        addHelpEntry("/f set leader <faction> <region> <town> <user name>", "Set the town's leader");
    }


}
