package me.fly.factions.impl.commands.faction.create;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.User;
import me.fly.factions.impl.model.FactionImpl;
import me.fly.factions.impl.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand extends CommandDivision {
    public CreateCommand() {
        addHelpEntry("/f create <name>", "Create a faction with the given name");

        addSubCommand("*", this);
    }

    @SuppressWarnings({"unused"})
    public boolean run(CommandSender sender, String name) {
        User user = USERS.get(((Player) sender).getUniqueId());

        if (user.getFaction() != null) {
            user.sendMessage(ChatColor.RED + "ERROR: you must leave your faction before creating a new one");

            return false;
        }

        if(!nameGood(name)) {
            user.sendMessage(ChatColor.RED + "ERROR: invalid characters. Use numbers, english letters, or underscores in your faction name (3-24 characters)");

            return false;
        }

        Faction faction = new FactionImpl(user, name);

        FACTIONS.set(name, faction);

        API.broadcast(ChatColor.LIGHT_PURPLE + "New faction created: " + ChatColor.YELLOW + name);

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.NOT_FACTION
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null)
        };
    }
}
