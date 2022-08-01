package dsds;

import fly.factions.api.model.Plot;
import fly.factions.impl.util.Plots;
import org.bukkit.World;

public class PlotLotComponent extends RectanglePlotComponent {
    public PlotLotComponent(Plot plot) {
        this(Plots.getX(plot.getLocationId()), Plots.getZ(plot.getLocationId()), Plots.getW(plot.getLocationId()));
    }

    public PlotLotComponent(int x, int z, int w) {
        super(x*16, z*16, x*16+15, z*16+15, Plots.getWorld(w));
    }
}
