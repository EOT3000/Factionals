package me.fly.factions.impl.commands.faction.admin;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.impl.commands.faction.admin.claim.AdminClaimCommand;
import me.fly.factions.impl.commands.faction.admin.delete.AdminDeleteCommand;
import me.fly.factions.impl.commands.faction.admin.leader.AdminLeaderCommand;
import me.fly.factions.impl.commands.faction.admin.mode.AdminModeCommand;
import me.fly.factions.impl.commands.faction.admin.transfer.AdminTransferCommand;

public class AdminCommand extends CommandDivision {
    public AdminCommand() {
        addSubCommand("claim", new AdminClaimCommand());
        addSubCommand("mode", new AdminModeCommand());
        addSubCommand("leader", new AdminLeaderCommand());
        addSubCommand("delete", new AdminDeleteCommand());
        addSubCommand("transfer", new AdminTransferCommand());
    }
}
