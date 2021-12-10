package fly.factions.impl.commands.faction.unclaim;

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

public class UnclaimCommand extends CommandDivision {
    public UnclaimCommand() {
        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String type) {
        Player player = (Player) sender;
        Chunk chunk = player.getLocation().getChunk();

        if(type.equalsIgnoreCase("all")) {
            for(Plot plot : USERS.get(player.getUniqueId()).getFaction().getPlots()) {
                plot.setFaction(null);
            }

            return true;
        } else if(type.equalsIgnoreCase("one")) {
            if (!unclaim0(chunk.getX(), chunk.getZ(), chunk.getWorld(), USERS.get(player.getUniqueId()).getFaction())) {
                player.sendMessage(ChatColor.RED + "ERROR: This chunk already unclaimed");
                return false;
            } else {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully unclaimed 1 chunk (" + chunk.getX() + "," + chunk.getZ() + "," + chunk.getWorld().getName() + ")");
                return true;
            }
        } else {
            player.sendMessage("I'm too lazy to make it properly formatted but you can't use that claim type so yeah this is an error kind of");
        }

        return false;
    }

    private static boolean unclaim0(int x, int z, World world, Faction faction) {
        Plot old = API.getRegistry(Plot.class, Integer.class).get(Plots.getLocationId(x, z, world));

        if(old == null) {
            return false;
        }

        old.setFaction(null);

        return true;
    }

    @Override
    public CommandDivision.ArgumentType[] getRequiredTypes() {
        return new CommandDivision.ArgumentType[] {
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
