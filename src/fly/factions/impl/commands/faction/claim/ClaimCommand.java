package fly.factions.impl.commands.faction.claim;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.Faction;
import fly.factions.api.model.Plot;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.impl.model.PlotImpl;
import fly.factions.impl.util.Pair;
import fly.factions.impl.util.Plots;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClaimCommand extends CommandDivision {
    public ClaimCommand() {
        addSubCommand("*", this);
        addSubCommand("", this);
    }

    public boolean run(CommandSender sender) {
        Player player = (Player) sender;
        Chunk chunk = player.getLocation().getChunk();

        if (!claim0(chunk.getX(), chunk.getZ(), chunk.getWorld(), USERS.get(player.getUniqueId()).getFaction())) {
            player.sendMessage(ChatColor.RED + "ERROR: This chunk already claimed");
            return false;
        } else {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully claimed 1 chunk (" + chunk.getX() + "," + chunk.getZ() + "," + chunk.getWorld().getName() + ")");
            return true;
        }
    }

    private static boolean claim0(int x, int z, World world, Faction faction) {
        Plot old = API.getRegistry(Plot.class, Integer.class).get(Plots.getLocationId(x, z, world));

        if(old != null) {
            return false;
        }

        Plot plot = new PlotImpl(x, z, world, faction);

        plot.setFaction(faction);

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.TERRITORY)
        };
    }
}
