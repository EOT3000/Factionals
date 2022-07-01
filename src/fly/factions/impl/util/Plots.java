package fly.factions.impl.util;

import fly.factions.Factionals;
import fly.factions.api.commands.CommandRegister;
import fly.factions.api.model.Faction;
import fly.factions.api.model.Plot;
import fly.factions.api.model.Region;
import fly.factions.api.model.User;
import fly.factions.api.permissions.FactionPermission;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.logging.Level;

public class Plots {
    static {
        try {
            getXMask = binaryToInteger("00000000000000000011111111111111");
            getZMask = binaryToInteger("00001111111111111100000000000000");
            getWMask = binaryToInteger("11110000000000000000000000000000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getXMask;
    public static int getZMask;
    public static int getWMask;

    private static int binaryToInteger(String binary) {
        char[] numbers = binary.toCharArray();
        int result = 0;
        for(int i=numbers.length - 1; i>=0; i--)
            if(numbers[i]=='1')
                result += Math.pow(2, (numbers.length-i - 1));
        return result;
    }

    public static Integer getLocationId(Location location) {
        return getLocationId(location.getChunk());
    }

    public static Integer getLocationId(Chunk chunk) {
        return getLocationId(chunk.getX(), chunk.getZ(), chunk.getWorld());
    }

    public static Integer getLocationId(int x, int z, World world) {
        return getLocationId(x, z, getWorldId(world));
    }

    public static Integer getLocationId(int x, int z, int world) {
        return ((x+8192) | ((z+8192) << 14)) | (world << 28);
    }

    public static int getX(int location) {
        return (location & getXMask)-8192;
    }

    public static int getZ(int location) {
        return ((location & getZMask) >> 14)-8192;
    }

    public static int getW(int location) {
        return (location & getWMask) >> 28;
    }

    public static int getWorldId(World world) {
        if(world == null) {
            System.out.println("uh oh");
            return 100;
        }

        switch (world.getName()) {
            case "world":
                return 0;

            case "world_nether":
                return 1;

            case "world_the_end":
                return 2;

            case "earth2":
                return 3;

            default:
                System.out.println(world.getName());
                return 100;
        }
    }

    // I have no idea why I made there be 2 methods. I'll keep it...

    public static World getWorld(int worldId) {
        return getWorld0(worldId);
    }

    public static World getWorld0(int worldId) {
        switch (worldId) {
            // Haha I love hard coding things

            case 0:
                return Bukkit.getWorld("world");

            case 1:
                return Bukkit.getWorld("world_nether");

            case 2:
                return Bukkit.getWorld("world_the_end");

            case 3:
                return Bukkit.getWorld("earth2");

            default:
                System.out.println(worldId);
                return null;
        }
    }

    public static void printChange(Plot plot, String context, String type, String doer) {
        int id = plot.getLocationId();

        Factionals.getFactionals().getLogger().info("{doer}: {context}({type}) {world},{x},{z}"
                .replace("{doer}", doer)
                .replace("{context}", context)
                .replace("{type}", type).replace("{world}", getWorld(getW(id)).getName())
                .replace("{x}", "" + getX(id))
                .replace("{z}", "" + getZ(id)));

        printChange(getWorld(getW(id)), getX(id), getZ(id), context, type, doer);
    }

    public static void printChange(World world, int x, int z, String context, String type, String doer) {
        Factionals.getFactionals().getLogger().info("{doer}: {context}({type}) {world},{x},{z}"
                .replace("{doer}", doer)
                .replace("{context}", context)
                .replace("{type}", type).replace("{world}", world.getName())
                .replace("{x}", "" + x)
                .replace("{z}", "" + z));
    }

    //TODO: Move commands into separate class

    //private static Factionals factionals = Factionals.getFactionals();

    private static void requireNotNull(Object o, String message, CommandSender sender) {
        if(o == null) {
            sender.sendMessage(message);
            throw new CommandRegister.ReturnNowException();
        }
    }

    private static void requirePermission(User user, FactionPermission permission, Faction faction) {
        if(!faction.hasPermission(user, permission)) {
            user.sendMessage(ChatColor.RED + "No permission");
            throw new CommandRegister.ReturnNowException();
        }
    }

    /*public static boolean setRegion(CommandSender sender, String a, String region) {
        CommandRegister.requirePlayer(sender);

        User user = factionals.getRegistry(User.class, UUID.class).get(((Player) sender).getUniqueId());
        Plot plot = factionals.getRegistry(Plot.class, Integer.class).get(getLocationId(((Player) sender).getLocation()));

        requireNotNull(plot, ChatColor.RED + "You are not in a plot", sender);
        requirePermission(user, FactionPermission.INTERNAL_MANAGEMENT, plot.getFaction());

        Region factionRegion = plot.getFaction().getRegion(region);

        requireNotNull(factionRegion, ChatColor.RED + "No such region", sender);

        plot.setAdministrator(factionRegion);

        return true;
    }*/
}
