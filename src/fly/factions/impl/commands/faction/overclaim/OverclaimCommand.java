package fly.factions.impl.commands.faction.overclaim;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.Plot;
import fly.factions.api.model.User;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.impl.util.Pair;
import fly.factions.impl.util.Plots;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OverclaimCommand extends CommandDivision {
    public OverclaimCommand() {
        addHelpEntry("/f overclaim", "Claims the chunk you are on for your faction");

        addSubCommand("", this);
        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender) {
        Player player = (Player) sender;
        User user = USERS.get(player.getUniqueId());
        Chunk chunk = player.getChunk();
        Plot plot = PLOTS.get(Plots.getLocationId(chunk));
        Location location = player.getLocation();

        if(plot != null && !plot.getFaction().equals(user.getFaction())) {
            if(plot.getFaction().getCurrentPower() < plot.getFaction().getPlots().size()) {
                user.getFaction().broadcast(ChatColor.YELLOW + player.getName() + ChatColor.LIGHT_PURPLE + " overclaimed " + ChatColor.YELLOW + plot.getFaction().getName());

                plot.getFaction().broadcast(ChatColor.YELLOW + player.getName() + ChatColor.LIGHT_PURPLE + " overclaimed you. " + ChatColor.GREEN + "{x},{y},{z},{w}".
                        replace("x", "" + location.getBlockX()).
                        replace("y", "" + location.getBlockY()).
                        replace("z", "" + location.getBlockZ()).
                        replace("w", location.getWorld().getName()));

                Plots.printChange(chunk.getWorld(), chunk.getX(), chunk.getZ(), "Overclaim of " + plot.getFaction().getId() + " for " + user.getFaction().getId(), "One", user.getName());

                plot.setFaction(user.getFaction());

                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "ERROR: this faction doesn't have low enough power for that you dumb fucking idiot");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "ERROR: this land is not claimed by a different faction");
        }

        return false;
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.TERRITORY),
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.RELATIONS),
        };
    }
}
