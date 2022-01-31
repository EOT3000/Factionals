package fly.factions.impl.listeners;

import fly.factions.Factionals;
import fly.factions.api.model.*;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.api.permissions.PermissionContext;
import fly.factions.api.permissions.PlotPermission;
import fly.factions.api.registries.Registry;
import fly.factions.impl.model.PlotImpl;
import fly.factions.impl.util.Plots;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.UUID;

public class PlotListener extends ListenerImpl {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Registry<Plot, Integer> pr = Factionals.getFactionals().getRegistry(Plot.class, Integer.class);

        Plot to = pr.get(Plots.getLocationId(e.getTo()));
        Plot from = pr.get(Plots.getLocationId(e.getFrom()));

        if(to != from) {
            User user = Factionals.getFactionals().getRegistry(User.class, UUID.class).get(e.getPlayer().getUniqueId());

            if (user.getFaction() != null && user.getFaction().hasPermission(user, FactionPermission.TERRITORY)) {
                if(Boolean.parseBoolean(String.valueOf(user.getAutoClaiming()))) {
                    if (to == null) {
                        Chunk chunk = e.getTo().getChunk();

                        if(user.getFaction().getPlots().size() + 1 >= user.getFaction().getCurrentPower()) {
                            user.sendMessage(ChatColor.RED + "ERROR: not enough power");

                            return;
                        }

                        Plot plot = new PlotImpl(chunk.getX(), chunk.getZ(), chunk.getWorld(), user.getFaction());

                        plot.setFaction(user.getFaction());

                        Plots.printChange(plot, "Claim for " + user.getFaction(), "Auto", user.getName());
                    }
                } else if(user.getAutoClaiming() == null) {
                    if (to != null) {
                        Chunk chunk = e.getTo().getChunk();

                        Plot plot = Factionals.getFactionals().getRegistry(Plot.class, Integer.class).get(Plots.getLocationId(chunk));

                        plot.setFaction(null);

                        Plots.printChange(plot, "Unclaim for " + user.getFaction(), "Auto", user.getName());
                    }
                } else if(user.getAutoClaiming() instanceof Region) {
                    if (to != null && to.getFaction().equals(user.getFaction())) {
                        Chunk chunk = e.getTo().getChunk();

                        Plot plot = Factionals.getFactionals().getRegistry(Plot.class, Integer.class).get(Plots.getLocationId(chunk));

                        plot.setAdministrator((Region) user.getAutoClaiming());

                        Plots.printChange(plot, "Region change for " + user.getFaction().getName() + " from " + plot.getAdministrator().getName() + " to " + ((Region) user.getAutoClaiming()).getName(), "Auto", user.getName());
                    }
                }
            }
        }

        if (f(to) == f(from)) {
            return;
        }

        if (to == null) {
            e.getPlayer().sendTitle(ChatColor.DARK_GREEN + "Entering Wilderness", ChatColor.GREEN + "It's dangerous to go alone", 5, 25, 5);
        } else {
            Faction f = to.getFaction();

            e.getPlayer().sendTitle(ChatColor.GOLD + "Entering " + ChatColor.YELLOW + f.getName(), ChatColor.YELLOW + f.getDescription(), 5, 25, 5);
        }
    }

    private Faction f(Plot p) {
        return p == null ? null : p.getFaction();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Registry<Plot, Integer> pr = Factionals.getFactionals().getRegistry(Plot.class, Integer.class);

        if (event.getClickedBlock() == null) {
            return;
        }

        Plot plot = pr.get(Plots.getLocationId(event.getClickedBlock().getLocation()));

        if (plot == null) {
            return;
        }

        Lot lot = null;

        if(plot.getAdministrator() instanceof Region) {
            lot = ((Region) plot.getAdministrator()).getLot(event.getClickedBlock().getLocation());
        }

        if (event.getItem() != null) {
            Material t = event.getItem().getType();

            if (!PermissionContext.canDoPlots(getUserFromPlayer(event.getPlayer()), plot.getAdministrator(), lot, PlotPermission.VEHICLES)) {
                if (Tag.ITEMS_BOATS.isTagged(t)) {
                    event.setCancelled(true);
                }

                if (t.equals(Material.MINECART) || t.equals(Material.CHEST_MINECART) || t.equals(Material.COMMAND_BLOCK_MINECART) || t.equals(Material.FURNACE_MINECART) ||
                        t.equals(Material.HOPPER_MINECART) || t.equals(Material.TNT_MINECART)) {
                    event.setCancelled(true);
                }
            }

            if (!PermissionContext.canDoPlots(getUserFromPlayer(event.getPlayer()), plot.getAdministrator(), lot, PlotPermission.BUILD)) {
                if (t.equals(Material.FLINT_AND_STEEL)) {
                    event.setCancelled(true);
                }
            }
        }

        for (PlotPermission permission : PlotPermission.values()) {
            if (permission.required(event.getClickedBlock(), event.getAction(), event.getPlayer().isSneaking()) && !PermissionContext.canDoPlots(getUserFromPlayer(event.getPlayer()), plot.getAdministrator(), lot, permission)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Registry<Plot, Integer> pr = Factionals.getFactionals().getRegistry(Plot.class, Integer.class);

        Plot plot = pr.get(Plots.getLocationId(event.getBlock().getLocation()));

        if(plot == null) {
            return;
        }

        Lot lot = null;

        if(plot.getAdministrator() instanceof Region) {
            lot = ((Region) plot.getAdministrator()).getLot(event.getBlock().getLocation());
        }


        if(!PermissionContext.canDoPlots(getUserFromPlayer(event.getPlayer()), plot.getAdministrator(), lot, PlotPermission.BUILD)) {
            event.setCancelled(true);
        }

        if(event.getBlock().getState() instanceof Container && !PermissionContext.canDoPlots(getUserFromPlayer(event.getPlayer()), plot.getAdministrator(), lot, PlotPermission.CONTAINER)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Registry<Plot, Integer> pr = Factionals.getFactionals().getRegistry(Plot.class, Integer.class);

        Plot plot = pr.get(Plots.getLocationId(event.getBlock().getLocation()));

        if(plot == null) {
            return;
        }

        Lot lot = null;

        if(plot.getAdministrator() instanceof Region) {
            lot = ((Region) plot.getAdministrator()).getLot(event.getBlock().getLocation());
        }

        if(!PermissionContext.canDoPlots(getUserFromPlayer(event.getPlayer()), plot.getAdministrator(), lot, PlotPermission.BUILD)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Registry<Plot, Integer> pr = Factionals.getFactionals().getRegistry(Plot.class, Integer.class);

        InventoryHolder holder = event.getInventory().getHolder();

        Plot plot = null;
        Lot lot = null;


        if(holder instanceof Entity && !(holder instanceof HumanEntity)) {
            plot = pr.get(Plots.getLocationId(((Entity) holder).getLocation()));

            if(plot != null && plot.getAdministrator() instanceof Region) {
                lot = ((Region) plot.getAdministrator()).getLot(((Entity) holder).getLocation());
            }
        }

        if(holder instanceof BlockState) {
            plot = pr.get(Plots.getLocationId(((BlockState) holder).getLocation()));

            if(plot != null && plot.getAdministrator() instanceof Region) {
                lot = ((Region) plot.getAdministrator()).getLot(((BlockState) holder).getLocation());
            }
        }

        if(holder instanceof DoubleChest) {
            plot = pr.get(Plots.getLocationId(((DoubleChest) holder).getLocation()));

            if(plot != null && plot.getAdministrator() instanceof Region) {
                lot = ((Region) plot.getAdministrator()).getLot(((DoubleChest) holder).getLocation());
            }
        }

        if(plot != null) {
            if(!PermissionContext.canDoPlots(getUserFromPlayer((Player) event.getPlayer()), plot.getAdministrator(), lot, PlotPermission.CONTAINER)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent event) {
        if(event.getEntity() instanceof Monster) {
            Plot plot = Factionals.getFactionals().getRegistry(Plot.class, Integer.class).get(Plots.getLocationId((event.getEntity()).getLocation()));

            if(plot != null) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onExplosion(BlockExplodeEvent event) {
        Registry<Plot, Integer> pr = Factionals.getFactionals().getRegistry(Plot.class, Integer.class);

        for(Block block : new ArrayList<>(event.blockList())) {
            Plot plot = pr.get(Plots.getLocationId(block.getLocation()));

            if(plot != null) {
                event.blockList().remove(block);
            }
        }
    }
}
