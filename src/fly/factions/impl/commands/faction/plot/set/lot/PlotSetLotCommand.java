package fly.factions.impl.commands.faction.plot.set.lot;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.*;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.impl.commands.faction.plot.set.lot.primary.PlotSetLotPrimaryCommand;
import fly.factions.impl.commands.faction.plot.set.lot.secondary.PlotSetLotSecondaryCommand;
import fly.factions.impl.commands.faction.plot.set.lot.tertiary.PlotSetLotTertiaryCommand;
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


        addSubCommand("primary", new PlotSetLotPrimaryCommand());
        addSubCommand("secondary", new PlotSetLotSecondaryCommand());
        addSubCommand("tertiary", new PlotSetLotTertiaryCommand());
    }

    public static boolean doSet(CommandSender sender, String x1s, String z1s, String x2s, String z2s, String region, String ids, int requirement, int type) {
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

        int area = (xG-xL+1)*(zG-zL+1);

        if(area < requirement) {
            sender.sendMessage(translate("&4ERROR: selected region not large enough. Is: &e" + area + "&4. Area should be at least &d" + requirement));
            return false;
        }

        if(faction.getRegion(region) == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the region " + ChatColor.YELLOW + region + " does not exist");

            return false;
        }

        for(int x = xL; x <= xG; x++) {
            for(int z = zL; z <= zG; z++) {
                Plot plot = PLOTS.get(Plots.getLocationId(new Location(world, x, 0, z)));
                LandAdministrator admin = plot.getAdministrator();

                if(faction != plot.getFaction() || !(admin instanceof Region && admin.getName().equalsIgnoreCase(region))) {
                    sender.sendMessage(ChatColor.RED + "ERROR: part of the selected area is not within your selected region or faction");

                    return false;
                }

                if(((Region) admin).getLot(world, x, z) != null) {
                    sender.sendMessage(ChatColor.RED + "ERROR: part of the selected area overlaps with another lot");

                    return false;
                }
            }
        }

        Lot lotObject = faction.getRegion(region).getLots().get(id);

        int xO1;
        int zO1;

        int xO2 = 0;
        int zO2 = 0;

        switch (type) {
            case 1:
                xO1 = lotObject.getXP();
                zO1 = lotObject.getZP();

                xO2 = lotObject.getXP2();
                zO2 = lotObject.getZP2();

                break;

            case 2:
                xO1 = lotObject.getXS();
                zO1 = lotObject.getZS();

                xO2 = lotObject.getXS2();
                zO2 = lotObject.getZS2();
                break;

            case 3:
                xO1 = lotObject.getXT();
                zO1 = lotObject.getZT();

                xO2 = lotObject.getXT2();
                zO2 = lotObject.getZT2();
                break;

            default:
                xO1 = Integer.MAX_VALUE;
                zO1 = Integer.MAX_VALUE;
                break;
        }

        faction.getRegion(region).setLotsAndValidate(world, xL, zL, xG, zG, xO1, zO1, xO2, zO2, lotObject, type);



        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully changed lot borders");

        return true;
    }
}
