package fly.factions.impl.model;

import fly.factions.Factionals;
import fly.factions.api.model.*;
import fly.factions.api.permissions.PlotPermission;
import fly.factions.impl.util.Pair;
import fly.factions.impl.util.Plots;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlotImpl implements Plot {
    private final int x;
    private final int z;
    private final World w;

    private Faction faction;
    private LandAdministrator admin;

    private Map<Pair<Integer, Integer>, Integer> areas = new HashMap<>();

    public PlotImpl(int x, int z, World w, Faction faction) {
        this.x = x;
        this.z = z;
        this.w = w;

        setFaction(faction);
    }

    @Override
    public void setFaction(Faction faction) {
        Factionals.getFactionals().getRegistry(Plot.class, Integer.class).set(getLocationId(), this);

        if(this.faction != null) {
            this.faction.removePlot(this);
        }

        this.faction = faction;

        if(faction == null) {
            Factionals.getFactionals().getRegistry(Plot.class, Integer.class).set(getLocationId(), null);
            return;
        }

        this.faction.addPlot(this);

        this.setAdministrator(faction);
    }

    @Override
    public int getLocationId() {
        return Plots.getLocationId(x, z, w);
    }

    @Override
    public Faction getFaction() {
        return faction;
    }

    @Override
    public LandAdministrator getAdministrator() {
        return admin;
    }

    @Override
    public void setAdministrator(LandAdministrator administrator) {
        if(admin != null && !(admin instanceof Faction)) {
            admin.removePlot(this);
        }

        this.admin = administrator;

        admin.addPlot(this);
    }

    @Override
    public String toString() {
        return "PlotImpl{" +
                "x=" + x +
                ", z=" + z +
                ", w=" + w +
                '}';
    }

    @Override
    public boolean hasPermission(User user, PlotPermission permission) {
        return false;
    }
}
