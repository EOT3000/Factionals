package me.fly.factions.impl.listeners;

import io.github.thebusybiscuit.slimefun4.api.events.MultiBlockInteractEvent;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import me.fly.factions.Factionals;
import me.fly.factions.api.model.Lot;
import me.fly.factions.api.model.Plot;
import me.fly.factions.api.model.Region;
import me.fly.factions.api.permissions.PermissionContext;
import me.fly.factions.api.permissions.PlotPermission;
import me.fly.factions.api.registries.Registry;
import me.fly.factions.impl.util.Plots;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

public class SlimefunListener extends ListenerImpl {
    @EventHandler
    public void onSlimefun(PlayerRightClickEvent event) {
        if(event.getClickedBlock().isPresent()) {
            Registry<Plot, Integer> pr = Factionals.getFactionals().getRegistry(Plot.class, Integer.class);

            Plot plot = pr.get(Plots.getLocationId(event.getClickedBlock().get().getLocation()));

            if (plot == null) {
                return;
            }

            Lot lot = null;

            if(plot.getAdministrator() instanceof Region) {
                lot = ((Region) plot.getAdministrator()).getLot(event.getClickedBlock().get().getLocation());
            }

            if(!PermissionContext.canDoPlots(getUserFromPlayer(event.getPlayer()), plot.getAdministrator(), lot, PlotPermission.BUILD)) {
                event.setUseBlock(Event.Result.DENY);
                event.setUseItem(Event.Result.DENY);
            }
        }
    }

    @EventHandler
    public void onSlimefunMultiblock(MultiBlockInteractEvent event) {
        Registry<Plot, Integer> pr = Factionals.getFactionals().getRegistry(Plot.class, Integer.class);

        Plot plot = pr.get(Plots.getLocationId(event.getClickedBlock().getLocation()));

        if (plot == null) {
            return;
        }

        Lot lot = null;

        if(plot.getAdministrator() instanceof Region) {
            lot = ((Region) plot.getAdministrator()).getLot(event.getClickedBlock().getLocation());
        }

        if(!PermissionContext.canDoPlots(getUserFromPlayer(event.getPlayer()), plot.getAdministrator(), lot, PlotPermission.BUILD)) {
            event.setCancelled(true);
        }
    }
}
