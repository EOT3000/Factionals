package me.fly.factions.impl.commands.faction.home;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.Region;
import me.fly.factions.api.model.User;
import me.fly.factions.api.permissions.FactionPermission;
import me.fly.factions.impl.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class HomeCommand extends CommandDivision {
    public HomeCommand() {
        addSubCommand("*", this);
        addSubCommand("", this);
    }

    public boolean run(CommandSender sender) {
        User user = USERS.get(((Player) sender).getUniqueId());

        if(user.getFaction() != null && user.getFaction().getHome() != null) {
            ((Player) sender).teleport(user.getFaction().getHome(), PlayerTeleportEvent.TeleportCause.COMMAND);
            user.sendMessage(ChatColor.GREEN + "Teleported you home.");

            return true;
        } else {
            user.sendMessage(ChatColor.RED + "Your faction has not set a home.");

            return false;
        }
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null)
        };
    }
}
