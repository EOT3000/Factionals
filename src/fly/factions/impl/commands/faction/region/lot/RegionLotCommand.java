package fly.factions.impl.commands.faction.region.lot;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.Plot;
import fly.factions.api.model.Region;
import fly.factions.api.model.User;
import fly.factions.impl.model.LotImpl;
import fly.factions.impl.util.Pair;
import fly.factions.impl.util.Plots;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegionLotCommand extends CommandDivision {
    public RegionLotCommand() {
        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String region) {
        User user = USERS.get(((Player) sender).getUniqueId());

        Region factionRegion = user.getFaction().getRegion(region);

        if(factionRegion == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the region " + ChatColor.YELLOW + region + " does not exist");

            return false;
        }

        factionRegion.setLot(factionRegion.getLots().size(), new LotImpl(factionRegion, factionRegion.getLots().size(), ((Player) sender).getWorld()));

        sender.sendMessage(ChatColor.LIGHT_PURPLE + " lot added: " + ChatColor.YELLOW + (factionRegion.getLots().size()-1));

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
