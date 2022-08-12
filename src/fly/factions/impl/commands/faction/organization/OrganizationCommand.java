package fly.factions.impl.commands.faction.organization;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.organization.claim.OrganizationClaimCommand;
import fly.factions.impl.commands.faction.organization.create.OrganizationCreateCommand;
import fly.factions.impl.commands.faction.organization.invite.OrganizationInviteCommand;
import fly.factions.impl.commands.faction.organization.join.OrganizationJoinCommand;

public class OrganizationCommand extends CommandDivision {
    public OrganizationCommand() {
        addSubCommand("create", new OrganizationCreateCommand());
        addSubCommand("claim", new OrganizationClaimCommand());
        addSubCommand("invite", new OrganizationInviteCommand());
        addSubCommand("join", new OrganizationJoinCommand());

        addHelpEntry("/f organization create <internation | private> <name>", "Creates an organization");
        addHelpEntry("/f organization claim", "Creates land for an international organization");
        addHelpEntry("/f organization invite <organization> <faction>", "");
        addHelpEntry("/f organization join <organization>", "");

    }
}
