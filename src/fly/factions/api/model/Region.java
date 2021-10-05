package fly.factions.api.model;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Region extends LandAdministrator<Plot> {
    Map<Integer, Lot> getLots();

    void setLot(int lotNumber, Lot lot);

    Collection<Town> getTowns();

    Town getTown(String name);

    void addTown(Town town);

    void removeTown(Town town);

    boolean setLotsAndValidate(World world, int x, int z, int x2, int z2, int xO1, int zO1, int xO2, int zO2, Lot lot, int type);

    default Lot getLot(Location location) {
        return getLot(location.getWorld(), location.getBlockX(), location.getBlockZ());
    }

    Lot getLot(World world, int x, int z);

    List<Location> getLotsLocations();
}
