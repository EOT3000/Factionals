package me.fly.factions.impl.commands.faction.organization.create;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.model.User;
import me.fly.factions.impl.model.InternationalOrganizationImpl;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OrganizationCreateCommand extends CommandDivision {
    public OrganizationCreateCommand() {
        addSubCommand("*", this);

        addHelpEntry("/f organization create <internation | private> <name>", "Creates an organization");
    }

    public boolean run(CommandSender sender, String type, String name) {
        User user = USERS.get(((Player) sender).getUniqueId());

        if(!nameGood(name)) {
            user.sendMessage(ChatColor.RED + "ERROR: invalid characters. Use numbers, english letters, or underscores in your faction name (3-24 characters)");

            return false;
        }

        if(ORGANIZATIONS.get(name) != null) {
            sender.sendMessage(ChatColor.RED + "ERROR: an organization with this name already exists");

            return false;
        }

        if(type.equalsIgnoreCase("international")) {
            new InternationalOrganizationImpl(name, user);

            sender.sendMessage("done");
        } else if(type.equalsIgnoreCase("private")) {
            sender.sendMessage("todo");
        }

        return true;
    }


}
