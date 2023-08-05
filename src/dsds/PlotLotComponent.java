package dsds;

import me.fly.factions.api.model.Plot;
import me.fly.factions.impl.util.Plots;

public class PlotLotComponent extends RectanglePlotComponent {
    public PlotLotComponent(Plot plot) {
        this(Plots.getX(plot.getLocationId()), Plots.getZ(plot.getLocationId()), Plots.getW(plot.getLocationId()));
    }

    public PlotLotComponent(int x, int z, int w) {
        super(x*16, z*16, x*16+15, z*16+15, Plots.getWorld(w));
    }
}
