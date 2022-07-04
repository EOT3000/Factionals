package fly.factions.impl.commands.faction.province.set.leader;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.Faction;
import fly.factions.api.model.Region;
import fly.factions.api.model.User;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.impl.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ProvinceSetLeaderCommand extends CommandDivision {
    public ProvinceSetLeaderCommand() {
        addHelpEntry("/f province set leader <province> <user>", "Set the province leader");


        addSubCommand("*", this);
    }

    @SuppressWarnings({"Pain please tell me what the NPE supression is", "unused", "deprecation"})
    public boolean run(CommandSender sender, String region, String newUser) {
        User user = USERS.get(Bukkit.getPlayer(sender.getName()).getUniqueId());
        Faction faction = user.getFaction();
        Region regionr = faction.getRegion(region);

        User victim = USERS.get(Bukkit.getOfflinePlayer(newUser).getUniqueId());

        if (regionr == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the province " + ChatColor.YELLOW + region + ChatColor.RED + " does not exist");

            return false;
        }

        if (!victim.getFaction().equals(faction)) {
            sender.sendMessage(ChatColor.RED + "ERROR: this user is not in your faction");

            return false;
        }

        regionr.setLeader(victim);

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully set province leader to " + ChatColor.YELLOW + newUser);

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.STRING,
                ArgumentType.USER
        };
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
