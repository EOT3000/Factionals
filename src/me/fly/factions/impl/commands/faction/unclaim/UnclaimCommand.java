package me.fly.factions.impl.commands.faction.unclaim;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.Plot;
import me.fly.factions.api.model.User;
import me.fly.factions.api.permissions.FactionPermission;
import me.fly.factions.impl.util.Pair;
import me.fly.factions.impl.util.Plots;
import org.apache.commons.lang.mutable.MutableInt;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UnclaimCommand extends CommandDivision {
    public UnclaimCommand() {
        addHelpEntry("/f unclaim <all | one | fill | auto>", "Unclaims some amount of chunks, or enables auto unclaiming");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String type) {
        Player player = (Player) sender;
        Chunk chunk = player.getLocation().getChunk();

        User user = USERS.get(player.getUniqueId());

        if(type.equalsIgnoreCase("all")) {
            if(!user.getFaction().hasPermission(user, FactionPermission.OWNER)) {
                sender.sendMessage(ChatColor.RED + "ERROR: no permission. Needed permission: OWNER");

                return false;
            }

            user.getFaction().broadcast(ChatColor.RED + "ALL LAND UNCLAIMED BY " + ChatColor.YELLOW + user.getName());
            user.getFaction().broadcast(ChatColor.RED + "ALL LAND UNCLAIMED BY " + ChatColor.YELLOW + user.getName());
            user.getFaction().broadcast(ChatColor.RED + "ALL LAND UNCLAIMED BY " + ChatColor.YELLOW + user.getName());
            user.getFaction().broadcast(ChatColor.RED + "ALL LAND UNCLAIMED BY " + ChatColor.YELLOW + user.getName());

            for(Plot plot : USERS.get(player.getUniqueId()).getFaction().getPlots()) {
                plot.setFaction(null);
            }

            Plots.printChange(chunk.getWorld(), chunk.getX(), chunk.getZ(), "Unclaim for " + user.getFaction().getId(), "All", user.getName());

            return true;
        } else if(type.equalsIgnoreCase("one")) {
            if (!unclaim0(chunk.getX(), chunk.getZ(), chunk.getWorld(), USERS.get(player.getUniqueId()).getFaction())) {
                player.sendMessage(ChatColor.RED + "ERROR: This chunk already unclaimed, or claimed by another faction");
                return false;
            } else {
                Plots.printChange(chunk.getWorld(), chunk.getX(), chunk.getZ(), "Unclaim for " + user.getFaction().getId(), "One", user.getName());

                player.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully unclaimed 1 chunk (" + chunk.getX() + "," + chunk.getZ() + "," + chunk.getWorld().getName() + ")");
                return true;
            }
        }  else if(type.equalsIgnoreCase("auto")) {
            user.setAutoClaiming("unclaim");

            user.sendMessage(ChatColor.LIGHT_PURPLE + "Now Auto-unclaiming, run '/f claim auto' twice to disable");

            return true;
        } else if(type.equalsIgnoreCase("fill")) {
            List<Pair<Integer, Integer>> list = fillNode(chunk.getX(), chunk.getZ(), chunk.getWorld(), new MutableInt(2500), new ArrayList<>(), user.getFaction());

            if(list == null) {
                sender.sendMessage(ChatColor.RED  + "ERROR: space too large (more than 2500 chunks)");

                return false;
            }

            for(Pair<Integer, Integer> claim : list) {
                unclaim0(claim.getKey(), claim.getValue(), chunk.getWorld(), user.getFaction());
            }

            Plots.printChange(chunk.getWorld(), chunk.getX(), chunk.getZ(), "Unclaim for " + user.getFaction().getId(), "Fill", user.getName());

            player.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully unclaimed area");

            return true;

        } else {
            player.sendMessage("I'm too lazy to make it properly formatted but you can't use that claim type so yeah this is an error kind of");
        }

        return false;
    }

    private static List<Pair<Integer, Integer>> fillNode(int x, int z, World w, MutableInt left, List<Pair<Integer, Integer>> permList, Faction f) {
        List<Pair<Integer, Integer>> list = new ArrayList<>();

        if(((int)left.getValue()) <= 0) {
            return null;
        }

        if(check(x+1, z, w, f) && !permList.contains(new Pair<>(x+1, z))) {
            list.add(new Pair<>(x+1, z));
            permList.add(new Pair<>(x+1, z));
            left.setValue(((int) left.getValue())-1);
        }
        if(check(x, z+1, w, f) && !permList.contains(new Pair<>(x, z+1))) {
            list.add(new Pair<>(x, z+1));
            permList.add(new Pair<>(x, z+1));
            left.setValue(((int) left.getValue())-1);
        }
        if(check(x-1, z, w, f) && !permList.contains(new Pair<>(x-1, z))) {
            list.add(new Pair<>(x-1, z));
            permList.add(new Pair<>(x-1, z));
            left.setValue(((int) left.getValue())-1);
        }
        if(check(x, z-1, w, f) && !permList.contains(new Pair<>(x, z-1))) {
            list.add(new Pair<>(x, z-1));
            permList.add(new Pair<>(x, z-1));
            left.setValue(((int) left.getValue())-1);
        }

        for(Pair<Integer, Integer> pair : list) {
            if(fillNode(pair.getKey(), pair.getValue(), w, left, permList, f) == null) {
                return null;
            }
        }

        return permList;
    }

    private static boolean check(int x, int z, World world, Faction faction) {
        Plot old = API.getRegistry(Plot.class, Integer.class).get(Plots.getLocationId(x, z, world));

        if(old == null || !old.getFaction().equals(faction)) {
            return false;
        }

        return true;
    }

    private static boolean unclaim0(int x, int z, World world, Faction faction) {
        Plot old = API.getRegistry(Plot.class, Integer.class).get(Plots.getLocationId(x, z, world));

        if(old == null || old.getFaction() != faction) {
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
