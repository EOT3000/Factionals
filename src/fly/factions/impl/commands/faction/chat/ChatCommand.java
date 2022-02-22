package fly.factions.impl.commands.faction.chat;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.User;
import fly.factions.impl.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand extends CommandDivision {
    public ChatCommand() {
        addHelpEntry("/f chat", "Toggles faction chat");

        addSubCommand("", this);
    }

    public boolean run(CommandSender sender) {
        User user = USERS.get(((Player) sender).getUniqueId());

        user.setFactionChat(!user.getFactionChat());

        user.sendMessage(ChatColor.LIGHT_PURPLE + (user.getFactionChat() ? "Enabled faction chat" : "Enabled public chat"));

        return true;
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair(CommandRequirement.REQUIRE_PLAYER, ""),
                new Pair(CommandRequirement.REQUIRE_MEMBER_FACTION, "")
        };
    }
}
