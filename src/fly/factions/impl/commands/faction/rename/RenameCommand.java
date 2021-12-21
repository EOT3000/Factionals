package fly.factions.impl.commands.faction.rename;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.Faction;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.impl.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class RenameCommand extends CommandDivision {
    private final Pattern pattern = Pattern.compile("^((?!([a-z]|[A-Z]|[0-9]|_)).)*$");

    public RenameCommand() {
        addHelpEntry("/f rename <name>", "Rename your faction");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String name) {
        Faction faction = USERS.get(((Player) sender).getUniqueId()).getFaction();

        if(!nameGood(name)) {
            sender.sendMessage(ChatColor.RED + "ERROR: invalid characters. Use numbers, english letters, or underscores in your name (3-24 characters)");

            return false;
        }

        faction.setName(name);

        sender.sendMessage(ChatColor.GREEN + "Success!");

        return true;
    }

    private boolean nameGood(String name) {
        if(pattern.matcher(name).matches()) {
            return false;
        }

        return name.length() <= 24 && name.length() >= 3;
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
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.OWNER)
        };
    }
}
