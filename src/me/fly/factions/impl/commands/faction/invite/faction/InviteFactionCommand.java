package me.fly.factions.impl.commands.faction.invite.faction;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.permissions.FactionPermission;
import me.fly.factions.impl.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InviteFactionCommand extends CommandDivision {
    public InviteFactionCommand() {
        addHelpEntry("/f invite add <user>", "Invite a user to the faction");

        addSubCommand("*", this);
    }

    @SuppressWarnings({"unused", "deprecation"})
    public boolean run(CommandSender sender, String name) {
        Faction faction = USERS.get(((Player) sender).getUniqueId()).getFaction();
        Faction invite = FACTIONS.get(name);

        if (invite != faction) {
            invite.addInvite(faction);

            invite.broadcast(ChatColor.LIGHT_PURPLE + "You have been invited to the faction " + ChatColor.YELLOW + faction.getName() + ChatColor.LIGHT_PURPLE + ". Join using " + ChatColor.YELLOW + "/f faction join " + faction.getName());

            faction.broadcast(ChatColor.YELLOW + invite.getName() + ChatColor.LIGHT_PURPLE + " (faction) has been invited to the faction by " + ChatColor.YELLOW + sender.getName());
            return true;
        }

        sender.sendMessage(ChatColor.RED + "ERROR: you cannot select your own faction");

        return false;
    }

    @Override
    public CommandDivision.ArgumentType[] getRequiredTypes() {
        return new CommandDivision.ArgumentType[] {
                CommandDivision.ArgumentType.USER
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.USER_ADD)
        };
    }
}
