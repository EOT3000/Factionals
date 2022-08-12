package fly.factions.impl.commands.faction.admin;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.admin.claim.AdminClaimCommand;
import fly.factions.impl.commands.faction.admin.delete.AdminDeleteCommand;
import fly.factions.impl.commands.faction.admin.leader.AdminLeaderCommand;
import fly.factions.impl.commands.faction.admin.mode.AdminModeCommand;
import fly.factions.impl.commands.faction.admin.transfer.AdminTransferCommand;

public class AdminCommand extends CommandDivision {
    public AdminCommand() {
        addSubCommand("claim", new AdminClaimCommand());
        addSubCommand("mode", new AdminModeCommand());
        addSubCommand("leader", new AdminLeaderCommand());
        addSubCommand("delete", new AdminDeleteCommand());
        addSubCommand("transfer", new AdminTransferCommand());
    }
}
