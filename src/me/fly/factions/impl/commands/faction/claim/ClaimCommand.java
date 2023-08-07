package me.fly.factions.impl.commands.faction.claim;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.Plot;
import me.fly.factions.api.model.User;
import me.fly.factions.api.permissions.FactionPermission;
import me.fly.factions.impl.model.PlotImpl;
import me.fly.factions.impl.util.Pair;
import me.fly.factions.impl.util.Plots;
import org.apache.commons.lang3.mutable.MutableInt;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ClaimCommand extends CommandDivision {
    public ClaimCommand() {
        addHelpEntry("/f claim <fill | one | auto>", "Claims either one chunk or fills in a hollow spot, or enabled autoclaim");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String type) {
        Player player = (Player) sender;
        Chunk chunk = player.getLocation().getChunk();
        User user = USERS.get(player.getUniqueId());

        if(type.equalsIgnoreCase("fill")) {
            List<Pair<Integer, Integer>> list = fillNode(chunk.getX(), chunk.getZ(), chunk.getWorld(), new MutableInt(2048), new ArrayList<>(), false);

            if(list == null) {
                sender.sendMessage(ChatColor.RED  + "ERROR: space too large (more than 2048 chunks)");

                return false;
            }

            if(user.getFaction().getPlots().size() + list.size() >= user.getFaction().getCurrentPower()) {
                sender.sendMessage(ChatColor.RED + "ERROR: not enough power");

                return false;
            }

            for(Pair<Integer, Integer> claim : list) {
                claim0(claim.getKey(), claim.getValue(), chunk.getWorld(), user.getFaction());
            }

            Plots.printChange(chunk.getWorld(), chunk.getX(), chunk.getZ(), "Claim for " + user.getFaction().getId(), "Fill", user.getName());

            player.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully filled area");

            return true;
        } else if(type.equalsIgnoreCase("fill-unsafe")) {
            List<Pair<Integer, Integer>> list = fillNode(chunk.getX(), chunk.getZ(), chunk.getWorld(), new MutableInt(2048), new ArrayList<>(), true);

            if(user.getFaction().getPlots().size() + list.size() >= user.getFaction().getCurrentPower()) {
                sender.sendMessage(ChatColor.RED + "ERROR: not enough power");

                return false;
            }

            for(Pair<Integer, Integer> claim : list) {
                claim0(claim.getKey(), claim.getValue(), chunk.getWorld(), user.getFaction());
            }

            Plots.printChange(chunk.getWorld(), chunk.getX(), chunk.getZ(), "Claim for " + user.getFaction().getId(), "Fill-Unsafe", user.getName());

            player.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully filled area");

            return true;
        } else if (type.equalsIgnoreCase("auto")) {
            user.setAutoClaiming(user.getFaction());

            user.sendMessage(ChatColor.LIGHT_PURPLE + "Now AutoClaiming for your faction. Run '/f stopClaiming' to stop");

            return true;
        }

        if(user.getFaction().getPlots().size() >= user.getFaction().getCurrentPower()) {
            sender.sendMessage(ChatColor.RED + "ERROR: not enough power");

            return false;
        }

        if (!claim0(chunk.getX(), chunk.getZ(), chunk.getWorld(), user.getFaction())) {
            player.sendMessage(ChatColor.RED + "ERROR: This chunk already claimed");
            return false;
        } else {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully claimed 1 chunk (" + chunk.getX() + "," + chunk.getZ() + "," + chunk.getWorld().getName() + ")");

            Plots.printChange(chunk.getWorld(), chunk.getX(), chunk.getZ(), "Claim for " + user.getFaction().getId(), "One", user.getName());

            return true;
        }
    }

    private static List<Pair<Integer, Integer>> fillNode(int x, int z, World w, MutableInt left, List<Pair<Integer, Integer>> permList, boolean unsafe) {
        List<Pair<Integer, Integer>> list = new ArrayList<>();

        if(((int)left.getValue()) <= 0) {
            if(unsafe) {
                return list;
            }

            return null;
        }

        if(check(x+1, z, w) && !permList.contains(new Pair<>(x+1, z))) {
            list.add(new Pair<>(x+1, z));
            permList.add(new Pair<>(x+1, z));
            left.setValue(((int) left.getValue())-1);
        }
        if(check(x, z+1, w) && !permList.contains(new Pair<>(x, z+1))) {
            list.add(new Pair<>(x, z+1));
            permList.add(new Pair<>(x, z+1));
            left.setValue(((int) left.getValue())-1);
        }
        if(check(x-1, z, w) && !permList.contains(new Pair<>(x-1, z))) {
            list.add(new Pair<>(x-1, z));
            permList.add(new Pair<>(x-1, z));
            left.setValue(((int) left.getValue())-1);
        }
        if(check(x, z-1, w) && !permList.contains(new Pair<>(x, z-1))) {
            list.add(new Pair<>(x, z-1));
            permList.add(new Pair<>(x, z-1));
            left.setValue(((int) left.getValue())-1);
        }

        for(Pair<Integer, Integer> pair : list) {
            if(fillNode(pair.getKey(), pair.getValue(), w, left, permList, unsafe) == null) {
                return null;
            }
        }

        return permList;
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
                ArgumentType.STRING
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
