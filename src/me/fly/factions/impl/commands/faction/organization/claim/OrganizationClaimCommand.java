package me.fly.factions.impl.commands.faction.organization.claim;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.Plot;
import me.fly.factions.api.model.User;
import me.fly.factions.api.model.organizations.InternationalOrganization;
import me.fly.factions.api.model.organizations.Organization;
import me.fly.factions.impl.model.PlotImpl;
import me.fly.factions.impl.util.Pair;
import me.fly.factions.impl.util.Plots;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OrganizationClaimCommand extends CommandDivision {
    public boolean run(CommandSender sender, String org) {
        Player player = ((Player) sender);

        User user = USERS.get(player.getUniqueId());
        Organization organization = ORGANIZATIONS.get(org);

        Chunk chunk = player.getChunk();

        if(!(organization instanceof InternationalOrganization)) {
            sender.sendMessage(ChatColor.RED + "ERROR: wrong type of organization");

            return false;
        }

        InternationalOrganization io = (InternationalOrganization) organization;

        if(organization.getLeader().equals(user)) {
            if(io.getPlots().size() >= io.getCurrentPower()) {
                sender.sendMessage(ChatColor.RED + "ERROR: not enough power");

                return false;
            }

            if (!claim0(chunk.getX(), chunk.getZ(), chunk.getWorld(), io)) {
                player.sendMessage(ChatColor.RED + "ERROR: This chunk already claimed");
                return false;
            } else {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully claimed 1 chunk (" + chunk.getX() + "," + chunk.getZ() + "," + chunk.getWorld().getName() + ")");

                Plots.printChange(chunk.getWorld(), chunk.getX(), chunk.getZ(), "Claim for " + organization.getId(), "One", user.getName());

                return true;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "ERROR: no permission");
        }

        return false;
    }

    private static boolean check(int x, int z, World world) {
        Plot old = API.getRegistry(Plot.class, Integer.class).get(Plots.getLocationId(x, z, world));

        if(old != null) {
            return false;
        }

        return true;
    }

    private static boolean claim0(int x, int z, World world, Faction faction) {
        if(!check(x, z, world)) {
            return false;
        }

        Plot plot = new PlotImpl(x, z, world, faction);

        plot.setFaction(faction);

        return true;
    }


    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.ORGANIZATION
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
        };
    }
}
