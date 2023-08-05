package me.fly.factions.impl.commands.faction.province.set;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.impl.commands.faction.province.set.format.ProvinceSetFormatCommand;
import me.fly.factions.impl.commands.faction.province.set.leader.ProvinceSetLeaderCommand;

public class ProvinceSetCommand extends CommandDivision {
    public ProvinceSetCommand() {
        //addHelpEntry("/f region set format fill <region> <fillRed> <fillGreen> <fillBlue> <fillOpacity>", "Set the region dynmap fill format");

        //addHelpEntry("/f region set format border <region> <borderRed> <borderGreen> <borderBlue>", "Set the region dynmap border color");

        addHelpEntry("/f province set format", "View province format commands");

        addHelpEntry("/f province set leader <province> <user>", "Set the province leader");

        addSubCommand("format", new ProvinceSetFormatCommand());

        addSubCommand("leader", new ProvinceSetLeaderCommand());
    }
}
