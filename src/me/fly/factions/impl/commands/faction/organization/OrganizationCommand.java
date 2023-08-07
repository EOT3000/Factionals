package me.fly.factions.impl.commands.faction.organization;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.impl.commands.faction.organization.claim.OrganizationClaimCommand;
import me.fly.factions.impl.commands.faction.organization.create.OrganizationCreateCommand;
import me.fly.factions.impl.commands.faction.organization.info.OrganizationInfoCommand;
import me.fly.factions.impl.commands.faction.organization.invite.OrganizationInviteCommand;
import me.fly.factions.impl.commands.faction.organization.join.OrganizationJoinCommand;

public class OrganizationCommand extends CommandDivision {
    public OrganizationCommand() {
        addSubCommand("create", new OrganizationCreateCommand());
        addSubCommand("claim", new OrganizationClaimCommand());
        addSubCommand("invite", new OrganizationInviteCommand());
        addSubCommand("join", new OrganizationJoinCommand());
        addSubCommand("info", new OrganizationInfoCommand());

        addHelpEntry("/f organization create <international | private> <name>", "Creates an organization");
        addHelpEntry("/f organization claim", "Creates land for an international organization");
        addHelpEntry("/f organization invite <organization> <faction>", "");
        addHelpEntry("/f organization join <organization>", "Join your faction into an organization");
        addHelpEntry("/f organization info <organization>", "View info about an organization");

    }
}
