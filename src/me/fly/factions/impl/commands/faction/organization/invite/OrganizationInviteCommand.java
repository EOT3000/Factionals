package me.fly.factions.impl.commands.faction.organization.invite;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.User;
import me.fly.factions.api.model.organizations.InternationalOrganization;
import me.fly.factions.impl.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OrganizationInviteCommand extends CommandDivision {
    public OrganizationInviteCommand() {
        addHelpEntry("/f organization invite <organization> <faction>", "");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String org, String fac) {
        User user = USERS.get(((Player) sender).getUniqueId());

        InternationalOrganization organization = (InternationalOrganization) ORGANIZATIONS.get(org);
        Faction faction = FACTIONS.get(fac);

        if(organization.getLeader().equals(user)) {
            organization.addInvite(faction);

            faction.broadcast(ChatColor.LIGHT_PURPLE + "Internation Organization " + ChatColor.YELLOW + organization.getName() + ChatColor.LIGHT_PURPLE
                    + " has invited your faction. Run " + ChatColor.GOLD + "/f organization join " + organization.getName());

            user.sendMessage(ChatColor.GREEN + "Successfully invited");

            return true;
        }

        return false;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.INTERNATIONAL_ORGANIZATION,
                ArgumentType.FACTION,
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null)
        };
    }
}
