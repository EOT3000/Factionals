package fly.factions.impl.commands.faction.plot.set.lot;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.*;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.impl.util.Pair;
import fly.factions.impl.util.Plots;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlotSetLotCommand extends CommandDivision {
    public PlotSetLotCommand() {
        addHelpEntry("/f plot set lot <x1> <z1> <x2> <z2> <region> <lot id>", "Set an area to a lot");


        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String x1s, String z1s, String x2s, String z2s, String region, String ids) {
        int x1 = Integer.parseInt(x1s);
        int z1 = Integer.parseInt(z1s);
        int x2 = Integer.parseInt(x2s);
        int z2 = Integer.parseInt(z2s);

        Location location = ((Player) sender).getLocation();

        User user = USERS.get(((Player) sender).getUniqueId());

        int id = Integer.parseInt(ids);

        int xL = Math.min(x1, x2);
        int zL = Math.min(z1, z2);

        int xG = Math.max(x1, x2);
        int zG = Math.max(z1, z2);

        World world = ((Player) sender).getWorld();

        Faction faction = PLOTS.get(Plots.getLocationId(new Location(world, x1, 0, z1))).getFaction();

        if(faction.getRegion(region) == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the region " + ChatColor.YELLOW + region + " does not exist");

            return false;
        }

        for(int x = xL; x <= xG; x++) {
            for(int z = zL; z <= zG; z++) {
                Plot plot = PLOTS.get(Plots.getLocationId(new Location(world, x, 0, z)));
                LandAdministrator admin = plot.getAdministrator();

                if(faction != plot.getFaction() || !(admin instanceof Region && admin.getName().equalsIgnoreCase(region))) {
                    return false;
                }
            }
        }

        Lot lotObject = null;

        for(int x = xL; x <= xG; x++) {
            for(int z = zL; z <= zG; z++) {
                Plot plot = PLOTS.get(Plots.getLocationId(new Location(world, x, 0, z)));

                plot.setLot(new Location(world, x, 0, z), lotObject == null ? lotObject = ((Region) plot.getAdministrator()).getLots().get(id) : lotObject);
            }
        }

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully changed lot borders");

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.INT,
                ArgumentType.INT,
                ArgumentType.INT,
                ArgumentType.INT,
                ArgumentType.STRING,
                ArgumentType.STRING,
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_REGION_LEADER, 4)
        };
    }
}
