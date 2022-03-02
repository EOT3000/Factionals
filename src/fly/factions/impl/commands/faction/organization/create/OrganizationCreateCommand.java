package fly.factions.impl.commands.faction.organization.create;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.model.User;
import fly.factions.impl.model.InternationalOrganizationImpl;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OrganizationCreateCommand extends CommandDivision {
    public boolean run(CommandSender sender, String type, String name) {
        User user = USERS.get(((Player) sender).getUniqueId());

        if(!nameGood(name)) {
            user.sendMessage(ChatColor.RED + "ERROR: invalid characters. Use numbers, english letters, or underscores in your faction name (3-24 characters)");

            return false;
        }

        if(type.equalsIgnoreCase("international")) {
            new InternationalOrganizationImpl(name, user);
        } else if(type.equalsIgnoreCase("private")) {

        }

        return true;
    }
}
