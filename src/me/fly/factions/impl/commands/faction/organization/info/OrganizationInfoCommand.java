package me.fly.factions.impl.commands.faction.organization.info;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.model.FactionComponent;
import me.fly.factions.api.model.Region;
import me.fly.factions.api.model.User;
import me.fly.factions.api.model.organizations.InternationalOrganization;
import me.fly.factions.api.model.organizations.Organization;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class OrganizationInfoCommand extends CommandDivision {
    public OrganizationInfoCommand() {

    }

    public boolean run(CommandSender sender, String org) {
        Organization organization = ORGANIZATIONS.get(org);


        if(organization instanceof InternationalOrganization) {
            InternationalOrganization intOrg = (InternationalOrganization) organization;

            sender.sendMessage(ChatColor.YELLOW + "International Organization " + ChatColor.GOLD + org);

            sender.sendMessage(ChatColor.DARK_AQUA + "Leader: " + ChatColor.WHITE + intOrg.getLeader().getName());

            String members = "";

            for(FactionComponent user : intOrg.getMemberOrganizations()) {
                members += (", " + user.getName());
            }

            members.replaceFirst(" ,", "");

            sender.sendMessage(translate("&3Member factions &b[&3" + intOrg.getMembers().size() + "&b]&3: &r" + members));
            sender.sendMessage(translate("&3Chunks&r/&3Power&r/&3Max Power&r: " + intOrg.getPlots().size() + "&7/&f" + intOrg.getCurrentPower() + "&7/&f" + intOrg.getMaxPower()));

            sender.sendMessage(translate("&3Description&r: " + intOrg.getDescription()));
        } else {
            sender.sendMessage(ChatColor.YELLOW + "Organization " + ChatColor.GOLD + org);

            sender.sendMessage(ChatColor.DARK_AQUA + "Leader: " + ChatColor.WHITE + organization.getLeader().getName());
        }

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.ORGANIZATION
        };
    }
}
