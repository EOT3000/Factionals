package fly.factions.api.model;

import fly.factions.impl.util.Pair;
import org.bukkit.Location;

import java.util.Map;

public interface Plot extends Savable, LandDivision {
    void setFaction(Faction faction);

    int getLocationId();

    Faction getFaction();

    LandAdministrator getAdministrator();

    void setAdministrator(LandAdministrator administrator);

    Lot getLot(Location location);

    void setLot(Location location, Lot lot);

    Map<Pair<Integer, Integer>, Integer> getLocations();
}
