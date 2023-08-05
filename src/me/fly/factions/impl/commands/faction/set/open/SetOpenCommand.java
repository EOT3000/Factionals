package me.fly.factions.impl.commands.faction.set.open;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.User;
import me.fly.factions.api.permissions.FactionPermission;
import me.fly.factions.impl.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetOpenCommand extends CommandDivision {
    public SetOpenCommand() {
        addHelpEntry("/f set open <true | false>", "Set the faction to open if true, or closed if false");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String what) {
        User user = USERS.get(((Player) sender).getUniqueId());
        boolean w = p(what);

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Set your faction to " + (user.getFaction().isOpen() ? "open" : "closed"));
        sender.sendMessage(ChatColor.RED + "**IMPORTANT**");
        sender.sendMessage(ChatColor.GOLD + "Setting your faction to open will let anyone join. Consider using regions to limit permissions of new players");

        user.getFaction().setOpen(w);

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.CHOICE
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.OWNER),
        };
    }

    public boolean p(String s) {
        return Boolean.parseBoolean(s) || (s.equalsIgnoreCase("yes"));
    }
}
