package fly.factions.impl.commands.faction.rename;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.Faction;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.impl.util.Pair;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RenameCommand extends CommandDivision {
    public RenameCommand() {
        addHelpEntry("/f rename <name>", "Rename your faction");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String name) {
        Faction faction = USERS.get(((Player) sender).getUniqueId()).getFaction();

        faction.setName(name);

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
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.OWNER)
        };
    }
}
