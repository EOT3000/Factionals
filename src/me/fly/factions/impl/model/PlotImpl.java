package me.fly.factions.impl.model;

import me.fly.factions.Factionals;
import me.fly.factions.api.model.*;
import me.fly.factions.api.permissions.PlotPermission;
import me.fly.factions.impl.util.Pair;
import me.fly.factions.impl.util.Plots;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
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

        this.setAdministrator(faction);

        if(faction == null) {
            Factionals.getFactionals().getRegistry(Plot.class, Integer.class).set(getLocationId(), null);
            return;
        }

        this.faction.addPlot(this);
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
        if(administrator != this.admin && this.admin instanceof Region) {
            int xc = x*16;
            int zc = z*16;

            for(int xa = 0; xa < 16; xa++) {
                for(int za = 0; za < 16; za++) {
                    Location loc = new Location(w, xc+xa, 0, zc+za);

                    if(((Region) this.admin).getLot(loc) != null) {
                        ((Region) this.admin).getLot(loc).delete();
                    }
                }
            }
        }

        if(admin != null && !(admin instanceof Faction)) {
            admin.removePlot(this);
        }

        this.admin = administrator;

        if(admin != null) {
            admin.addPlot(this);
        }
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
