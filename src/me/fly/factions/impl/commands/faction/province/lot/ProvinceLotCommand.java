package me.fly.factions.impl.commands.faction.province.lot;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Region;
import me.fly.factions.api.model.User;
import me.fly.factions.impl.model.LotImpl;
import me.fly.factions.impl.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProvinceLotCommand extends CommandDivision {
    public ProvinceLotCommand() {
        addHelpEntry("/f region lot <province name>", "Add a lot");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String region) {
        User user = USERS.get(((Player) sender).getUniqueId());

        Region factionRegion = user.getFaction().getRegion(region);

        if(factionRegion == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the province " + ChatColor.YELLOW + region + " does not exist");

            return false;
        }

        factionRegion.setLot(factionRegion.getLots().size(), new LotImpl(factionRegion, factionRegion.getLots().size(), ((Player) sender).getWorld()));

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Lot added: " + ChatColor.YELLOW + (factionRegion.getLots().size()-1));

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
