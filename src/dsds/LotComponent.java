package dsds;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

public interface LotComponent {
    Object getWorld();

    boolean isInArea(Location location);

    List<Location> getPerimeter();

    default boolean isTouching(LotComponent other) {
        if(!getWorld().equals(other.getWorld())) {
            return false;
        }

        for(Location location1 : getPerimeter()) {
            for(Location location2 : other.getPerimeter()) {
                if(location1.distance(location2) <= 1) {
                    return true;
                }
            }
        }

        return false;
    }

    default boolean isIntersecting(LotComponent other) {
        if(!getWorld().equals(other.getWorld())) {
            return false;
        }

        for(Location location1 : getPerimeter()) {
            for(Location location2 : other.getPerimeter()) {
                if(location1.distance(location2) == 0) {
                    return true;
                }
            }
        }

        return false;
    }
}
