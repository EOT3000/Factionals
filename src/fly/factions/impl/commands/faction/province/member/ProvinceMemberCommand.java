package fly.factions.impl.commands.faction.province.member;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.province.member.add.ProvinceMemberAddCommand;
import fly.factions.impl.commands.faction.province.member.remove.ProvinceMemberRemoveCommand;

public class ProvinceMemberCommand extends CommandDivision {
    public ProvinceMemberCommand() {
        addHelpEntry("/f province member remove <province> <user>", "Removes a user from a province");
        addHelpEntry("/f province member add <province> <user>", "Adds a user to a province");

        addSubCommand("remove", new ProvinceMemberRemoveCommand());
        addSubCommand("add", new ProvinceMemberAddCommand());
    }
}
