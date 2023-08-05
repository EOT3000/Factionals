package me.fly.factions.impl.commands.faction.organization.join;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.User;
import me.fly.factions.api.model.organizations.InternationalOrganization;
import me.fly.factions.api.permissions.FactionPermission;
import me.fly.factions.impl.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OrganizationJoinCommand extends CommandDivision {
    public OrganizationJoinCommand() {
        addHelpEntry("/f organization join <organization>", "");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String org) {
        User user = USERS.get(((Player) sender).getUniqueId());

        InternationalOrganization organization = (InternationalOrganization) ORGANIZATIONS.get(org);

        if(organization.hasInviteFrom(user.getFaction())) {
            organization.addMemberOrganization(user.getFaction());

            organization.broadcast(ChatColor.LIGHT_PURPLE + "Faction " + ChatColor.YELLOW + user.getFaction().getName() + ChatColor.LIGHT_PURPLE
                    + " has joined " + ChatColor.YELLOW + organization.getName());

            return true;
        } else {
            sender.sendMessage("No invite");
        }

        return false;
    }

    @Override
    public CommandDivision.ArgumentType[] getRequiredTypes() {
        return new CommandDivision.ArgumentType[] {
                CommandDivision.ArgumentType.INTERNATIONAL_ORGANIZATION,
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.RELATIONS),
        };
    }
}
