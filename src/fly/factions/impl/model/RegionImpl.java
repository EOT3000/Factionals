package fly.factions.impl.model;

import fly.factions.api.model.*;
import fly.factions.api.permissions.Permissibles;
import fly.factions.impl.util.Plots;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class RegionImpl extends AbstractLandAdministrator<Plot> implements Region {
    private Map<Integer, Lot> lots = new HashMap<>();

    private Faction faction;

    private Set<Town> towns = new HashSet<>();

    private Map<Location, Lot> lotMap = new HashMap<>();

    public RegionImpl(String name, User leader, Faction faction) {
        super(name, leader);

        this.faction = faction;

        Permissibles.add(faction.getName() + ":" + name, this);
        Permissibles.add(getId(), this);
    }

    @Override
    public String getDesc() {
        return "<div class=\"regioninfo\"><div class=\"infowindow\"><span style=\"font-size:120%;\">" + name + "</span><br />" +
                "<span style=\"font-weight:bold;\">Faction: " + faction.getName() + "</span><br />" +
                "<span style=\"font-weight:bold;\">Leader: " + getLeader().getName() + "</span></div></div>";
    }

    @Override
    public double getBorderOpacity() {
        return 1;
    }

    @Override
    public String getId() {
        return faction.getId() + "-region-" + name;
    }

    @Override
    public ItemStack getItem() {
        //TODO: Fix
        return new ItemStack(Material.AIR);
    }

    @Override
    public boolean userHasPlotPermissions(User user, boolean owner, boolean pub) {
        return (!owner && members.contains(user)) || leader.equals(user) || faction.getLeader().equals(user);
    }

    @Override
    public void removeMember(User user) {
        members.remove(user);

        if(user.equals(leader)) {
            this.leader = faction.getLeader();
        }
    }

    @Override
    public Map<Integer, Lot> getLots() {
        return new HashMap<>(lots);
    }

    @Override
    public void setLot(int lotNumber, Lot lot) {
        lots.put(lotNumber, lot);
    }

    @Override
    public Faction getFaction() {
        return faction;
    }

    @Override
    public Collection<Town> getTowns() {
        return new ArrayList<>(towns);
    }

    @Override
    public Town getTown(String name) {
        for (Town town : towns) {
            if (town.getName().equalsIgnoreCase(name)) {
                return town;
            }
        }

        return null;
    }

    @Override
    public void addTown(Town town) {
        towns.add(town);
    }

    @Override
    public void removeTown(Town town) {
        towns.remove(town);
    }

    @Override
    public boolean setLotsAndValidate(World world, int xL, int zL, int xG, int zG, int xO1, int zO1, int xO2, int zO2, Lot lot, int type) {
        if(!lot.getWorld().equals(world)) {
            return false;
        }

        if(xO1 != Integer.MAX_VALUE) {
            int xOL = Math.min(xO1, xO2);
            int zOL = Math.min(zO1, zO2);

            int xOG = Math.max(xO1, xO2);
            int zOG = Math.max(zO1, zO2);

            if(xO1 != Integer.MAX_VALUE-1) {
                for (int x = xOL; x <= xOG; x++) {
                    for (int z = zOL; z <= zOG; z++) {
                        lotMap.remove(new Location(world, x, 0, z));
                    }
                }
            }

            for (int x = xL; x <= xG; x++) {
                for (int z = zL; z <= zG; z++) {
                    Location loc = new Location(world, x, 0, z);

                    Plot plot = factionals.getRegistry(Plot.class, Integer.class).get(Plots.getLocationId(loc));

                    if(plot != null && plot.getAdministrator().equals(this)) {
                        lotMap.put(loc, lot);

                        break;
                    }
                }
            }

            switch (type) {
                case 1:
                    lot.setXP(xL);
                    lot.setZP(zL);

                    lot.setXP2(xG);
                    lot.setZP2(zG);

                    break;

                case 2:
                    lot.setXS(xL);
                    lot.setZS(zL);

                    lot.setXS2(xG);
                    lot.setZS2(zG);

                    break;

                case 3:
                    lot.setXT(xL);
                    lot.setZT(zL);

                    lot.setXT2(xG);
                    lot.setZT2(zG);

                    break;
            }

            return true;
        }

        return false;
    }

    @Override
    public Lot getLot(World world, int x, int z) {
        return lotMap.get(new Location(world, x, 0, z));
    }

    @Override
    public List<Location> getLotsLocations() {
        //TODO: unique immutable locations

        return new ArrayList<>(lotMap.keySet());
    }
}
