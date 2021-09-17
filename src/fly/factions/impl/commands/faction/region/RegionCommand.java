package fly.factions.impl.commands.faction.region;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.region.create.RegionCreateCommand;
import fly.factions.impl.commands.faction.region.info.RegionInfoCommand;
import fly.factions.impl.commands.faction.region.lot.RegionLotCommand;
import fly.factions.impl.commands.faction.region.set.RegionSetCommand;

public class RegionCommand extends CommandDivision {
    public RegionCommand() {
        addHelpEntry("/f region create <name>", "Create a region with the given name");

        addHelpEntry("/f region set", "View region settings commands");

        addHelpEntry("/f region lot <region name>", "Add a lot");

        addHelpEntry("/f region info <faction name> <region name>", "View information about a region");


        addSubCommand("set", new RegionSetCommand());

        addSubCommand("create", new RegionCreateCommand());

        addSubCommand("lot", new RegionLotCommand());

        addSubCommand("info", new RegionInfoCommand());
    }
}
