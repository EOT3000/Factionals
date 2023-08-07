package me.fly.factions.impl.commands.faction.stopClaiming;

import me.fly.factions.api.claiming.ClaimType;
import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.User;
import me.fly.factions.impl.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopClaimingCommand extends CommandDivision {
    public StopClaimingCommand() {
        addHelpEntry("/f stopClaiming", "Stop claiming");

        addSubCommand("", this);
    }

    public boolean run(CommandSender sender) {
        User user = USERS.get(((Player) sender).getUniqueId());

        user.setAutoClaiming(ClaimType.NONE);
        user.sendMessage(ChatColor.LIGHT_PURPLE + "No longer auto claiming or unclaiming.");

        return true;
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
        };
    }
}
