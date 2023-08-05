package me.fly.factions.impl.commands.faction.town.create;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Region;
import me.fly.factions.api.model.User;
import me.fly.factions.impl.model.TownImpl;
import me.fly.factions.impl.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TownCreateCommand extends CommandDivision {
    public TownCreateCommand() {
        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String region, String name) {
        User user = USERS.get(Bukkit.getPlayer(sender.getName()).getUniqueId());

        Region factionRegion = user.getFaction().getRegion(region);

        if(factionRegion == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the region " + ChatColor.YELLOW + region + " does not exist");

            return false;
        }

        if (user.getFaction().getRegion(name) != null) {
            user.sendMessage(ChatColor.RED + "ERROR: the town " + ChatColor.YELLOW + name + ChatColor.RED + " already exists");
            return false;
        }

        user.getFaction().getRegion(region).addTown(new TownImpl(name, user, factionRegion));

        user.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully created town " + ChatColor.YELLOW + name);

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.STRING
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_REGION_LEADER, 0)
        };
    }
}
