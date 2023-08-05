package me.fly.factions.impl.commands.faction.plot.set.region;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.LandAdministrator;
import me.fly.factions.api.model.Region;
import me.fly.factions.api.model.User;
import me.fly.factions.api.permissions.FactionPermission;
import me.fly.factions.impl.util.Pair;
import me.fly.factions.impl.util.Plots;
import me.fly.factions.api.model.Plot;
import org.apache.commons.lang.mutable.MutableInt;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlotSetRegionCommand extends CommandDivision {
    public PlotSetRegionCommand() {
        addHelpEntry("/f plot set region <region> <one | fill>", "Set a plot's region, either one chunk or fill");


        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String region, String type) {
        Location location = ((Player) sender).getLocation();

        User user = USERS.get(((Player) sender).getUniqueId());

        Plot plot = PLOTS.get(Plots.getLocationId(location));

        if(plot == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: you are not standing in a plot");

            return false;
        }

        Region factionRegion = user.getFaction().getRegion(region);

        if(factionRegion == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the region " + ChatColor.YELLOW + region + ChatColor.RED + " does not exist");

            return false;
        }

        if(type.equalsIgnoreCase("fill")) {
            List<Pair<Integer, Integer>> list = fillNode(((Player) sender).getChunk().getX(), ((Player) sender).getChunk().getZ(), ((Player) sender).getWorld(), new MutableInt(2000), new ArrayList<>(), plot.getAdministrator());

            if(list == null) {
                sender.sendMessage(ChatColor.RED + "ERROR: area to fill more than 2000");
            } else {
                for(Pair<Integer, Integer> pair : list) {
                    Plot p = API.getRegistry(Plot.class, Integer.class).get(Plots.getLocationId(pair.getKey(), pair.getValue(), ((Player) sender).getWorld()));

                    if(factionRegion.getFaction().equals(p.getFaction())) {
                        p.setAdministrator(factionRegion);
                    } else {
                        sender.sendMessage(ChatColor.RED + "ERROR: No. Just no");

                        if(sender.getName().equalsIgnoreCase("Thicc_Nicc07")) {
                            sender.sendMessage(ChatColor.DARK_RED + "STOP SLICK OMG JUST STOP");
                        }

                        return false;
                    }
                }

                return true;
            }

            return false;
        } else if(type.equalsIgnoreCase("auto")) {
            user.setAutoClaiming(factionRegion);
        }

        if(factionRegion.getFaction().equals(plot.getFaction())) {
            plot.setAdministrator(factionRegion);
        } else {
            sender.sendMessage(ChatColor.RED + "ERROR: No. Just no");

            if(sender.getName().equalsIgnoreCase("Thicc_Nicc07")) {
                sender.sendMessage(ChatColor.DARK_RED + "STOP SLICK OMG JUST STOP");
            }

            return false;
        }

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully set plot region");

        return true;
    }

    private static List<Pair<Integer, Integer>> fillNode(int x, int z, World w, MutableInt left, List<Pair<Integer, Integer>> permList, LandAdministrator region) {
        List<Pair<Integer, Integer>> list = new ArrayList<>();

        if(((int)left.getValue()) <= 0) {
            return null;
        }

        if(check(x+1, z, w, region) && !permList.contains(new Pair<>(x+1, z))) {
            list.add(new Pair<>(x+1, z));
            permList.add(new Pair<>(x+1, z));
            left.setValue(((int) left.getValue())-1);
        }
        if(check(x, z+1, w, region) && !permList.contains(new Pair<>(x, z+1))) {
            list.add(new Pair<>(x, z+1));
            permList.add(new Pair<>(x, z+1));
            left.setValue(((int) left.getValue())-1);
        }
        if(check(x-1, z, w, region) && !permList.contains(new Pair<>(x-1, z))) {
            list.add(new Pair<>(x-1, z));
            permList.add(new Pair<>(x-1, z));
            left.setValue(((int) left.getValue())-1);
        }
        if(check(x, z-1, w, region) && !permList.contains(new Pair<>(x, z-1))) {
            list.add(new Pair<>(x, z-1));
            permList.add(new Pair<>(x, z-1));
            left.setValue(((int) left.getValue())-1);
        }

        for(Pair<Integer, Integer> pair : list) {
            if(fillNode(pair.getKey(), pair.getValue(), w, left, permList, region) == null) {
                return null;
            }
        }

        return permList;
    }

    private static boolean check(int x, int z, World world, LandAdministrator region) {
        Plot plot = API.getRegistry(Plot.class, Integer.class).get(Plots.getLocationId(x, z, world));

        if(plot == null || plot.getAdministrator() != region) {
            return false;
        }

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
