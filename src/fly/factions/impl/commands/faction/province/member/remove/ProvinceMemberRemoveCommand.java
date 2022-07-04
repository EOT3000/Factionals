package fly.factions.impl.commands.faction.province.member.remove;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.model.Region;
import fly.factions.api.model.User;
import fly.factions.api.permissions.FactionPermission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProvinceMemberRemoveCommand extends CommandDivision {
    public ProvinceMemberRemoveCommand() {
        addHelpEntry("/f province member remove <region> <user>", "Removes a user from a province");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String region, String member) {
        User senderUser = USERS.get(((Player) sender).getUniqueId());
        Region factionRegion = senderUser.getFaction().getRegion(region);

        if(factionRegion != null) {
            if(factionRegion.getLeader().equals(senderUser) || factionRegion.getFaction().hasPermission(senderUser, FactionPermission.OWNER)) {
                User victim = USERS.get(Bukkit.getOfflinePlayer(member).getUniqueId());

                if(victim.getFaction().equals(factionRegion.getFaction())) {

                    factionRegion.removeMember(victim);

                    sender.sendMessage(ChatColor.GREEN + "Success!");

                    return true;
                }

                sender.sendMessage(ChatColor.RED + "ERROR: that user is not in your faction");

                return false;
            }

            sender.sendMessage(ChatColor.RED + "ERROR: no permission");

            return false;
        }

        sender.sendMessage(ChatColor.RED + "ERROR: that province doesn't exist");

        return false;
    }
}
