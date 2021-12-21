package fly.factions.impl.commands.faction.set.description;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.Faction;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.impl.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetDescriptionCommand extends CommandDivision {
    public SetDescriptionCommand() {
        addHelpEntry("/f set description <desc>", "Set your faction's description");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String desc) {
        Faction faction = USERS.get(((Player) sender).getUniqueId()).getFaction();

        faction.setDescription(desc);

        sender.sendMessage(ChatColor.GREEN + "Success!");

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
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.INTERNAL_MANAGEMENT)
        };
    }
}
