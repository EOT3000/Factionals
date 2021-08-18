package fly.factions.impl.commands.faction.invite.add;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.Faction;
import fly.factions.api.model.User;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.impl.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InviteAddCommand extends CommandDivision {
    public InviteAddCommand() {
        addHelpEntry("/f invite add <user>", "Invite a user to the faction");

        addSubCommand("*", this);
    }

    @SuppressWarnings({"unused", "deprecation"})
    public boolean run(CommandSender sender, String name) {
        Faction faction = USERS.get(((Player) sender).getUniqueId()).getFaction();
        User user = USERS.get(Bukkit.getOfflinePlayer(name).getUniqueId());

        if (user.getFaction() != faction) {
            user.addInvite(faction);

            user.sendMessage(ChatColor.LIGHT_PURPLE + "You have been invited to the faction " + ChatColor.YELLOW + faction.getName() + ChatColor.LIGHT_PURPLE + ". Join using /f join ");

            faction.broadcast(ChatColor.YELLOW + name + ChatColor.LIGHT_PURPLE + " has been invited to the faction by " + ChatColor.YELLOW + sender.getName());
            return true;
        }

        sender.sendMessage(ChatColor.RED + "ERROR: user is already in your faction");

        return false;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.USER
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.USERS)
        };
    }
}

