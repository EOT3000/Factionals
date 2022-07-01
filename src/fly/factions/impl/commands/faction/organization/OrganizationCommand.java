package fly.factions.impl.commands.faction.organization;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.organization.claim.OrganizationClaimCommand;
import fly.factions.impl.commands.faction.organization.create.OrganizationCreateCommand;

public class OrganizationCommand extends CommandDivision {
    public OrganizationCommand() {
        addSubCommand("create", new OrganizationCreateCommand());
        addSubCommand("claim", new OrganizationClaimCommand());

        addHelpEntry("/f organization create <internation | private> <name>", "Creates an organization");
        addHelpEntry("/f organization claim", "Creates land for an international organization");
    }
}
