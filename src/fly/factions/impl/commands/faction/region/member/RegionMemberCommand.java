package fly.factions.impl.commands.faction.region.member;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.region.member.add.RegionMemberAddCommand;
import fly.factions.impl.commands.faction.region.member.remove.RegionMemberRemoveCommand;

public class RegionMemberCommand extends CommandDivision {
    public RegionMemberCommand() {
        addHelpEntry("/f region member remove <region> <user>", "Removes a user from a region");
        addHelpEntry("/f region member add <region> <user>", "Add a user to a region");

        addSubCommand("remove", new RegionMemberRemoveCommand());
        addSubCommand("add", new RegionMemberAddCommand());
    }
}
