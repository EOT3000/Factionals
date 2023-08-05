package me.fly.factions.impl.commands.faction.disband;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.permissions.FactionPermission;
import me.fly.factions.impl.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisbandCommand extends CommandDivision {
    public DisbandCommand() {
        addSubCommand("*", this);
        addSubCommand("", this);
    }

    public boolean run(CommandSender sender) {
        Faction faction = USERS.get(((Player) sender).getUniqueId()).getFaction();

        if(faction.getPlots().size() == 0) {
            faction.delete();

            sender.sendMessage(ChatColor.GREEN + "Success!");

            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "ERROR: you must unclaim all chunks (/f unclaim all) before disbanding to confirm you want to do this");
        }

        return false;
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.OWNER)
        };
    }
}
