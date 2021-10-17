package fly.factions.impl.util;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Objects;

public class LocationStorage {
    public final int x;
    public final int z;

    public final World world;

    public LocationStorage(int x, int z, World world) {
        this.x = x;
        this.z = z;

        this.world = world;
    }

    public Location toLocation() {
        return new Location(world, x, 0, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationStorage that = (LocationStorage) o;
        return x == that.x && z == that.z && world.equals(that.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z, world);
    }
}
