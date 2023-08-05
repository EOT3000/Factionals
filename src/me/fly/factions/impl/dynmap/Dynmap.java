package me.fly.factions.impl.dynmap;

import me.fly.factions.Factionals;
import me.fly.factions.api.model.*;
import me.fly.factions.impl.util.LocationStorage;
import me.fly.factions.impl.util.Plots;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerSet;

import java.util.*;

/**
 * Copyright of https://github.com/webbukkit
 *
 * https://github.com/webbukkit/Dynmap-Factions
 *
 * This code has been modified
 */

public class Dynmap {
    private Map<String, AreaMarker> markers = new HashMap<>();

    private MarkerSet facSet;
    private MarkerSet outSet;
    private MarkerSet regSet;
    private MarkerSet twnSet;
    private MarkerSet lotSet;
    private DynmapAPI api;



    public Dynmap() {
        api = (DynmapAPI) Bukkit.getPluginManager().getPlugin("dynmap");

        facSet = api.getMarkerAPI().getMarkerSet("factions.factionals.dynmap");
        outSet = api.getMarkerAPI().getMarkerSet("outline.factionals.dynmap");
        regSet = api.getMarkerAPI().getMarkerSet("regions.factionals.dynmap");
        twnSet = api.getMarkerAPI().getMarkerSet("towns.factionals.dynmap");
        lotSet = api.getMarkerAPI().getMarkerSet("lots.factionals.dynmap");

        if(facSet == null) {
            facSet = api.getMarkerAPI().createMarkerSet("factions.factionals.dynmap", "Factions", null, false);
        }
        if(outSet == null) {
            outSet = api.getMarkerAPI().createMarkerSet("outline.factionals.dynmap", "Outline", null, false);
        }
        if(regSet == null) {
            regSet = api.getMarkerAPI().createMarkerSet("regions.factionals.dynmap", "Regions", null, false);
        }
        if(twnSet == null) {
            twnSet = api.getMarkerAPI().createMarkerSet("towns.factionals.dynmap", "Towns", null, false);
        }
        if(lotSet == null) {
            lotSet = api.getMarkerAPI().createMarkerSet("lots.factionals.dynmap", "Lots", null, false);
        }

        facSet.setLayerPriority(100);
        outSet.setLayerPriority(75);
        regSet.setLayerPriority(50);
        twnSet.setLayerPriority(25);
        lotSet.setLayerPriority(0);

        Bukkit.getScheduler().runTaskTimer(Factionals.getFactionals(), this::runMacro, 300, 600);
        Bukkit.getScheduler().runTaskTimer(Factionals.getFactionals(), this::runMicro, 600, 1200);
    }

    public void runMacro() {
        List<StoredData> list = new ArrayList<>();

        for (Faction faction : Factionals.getFactionals().getRegistry(Faction.class).list()) {
            for (Region region : faction.getRegions()) {
                StoredData r = new StoredData();

                r.borderRGB = region.getBorderColor().asRGB();
                r.fillRGB = region.getFillColor().asRGB();
                r.fillO = region.getFillOpacity();

                r.id = region.getId();
                r.clazz = region.getClass().getName();
                r.desc = region.getDesc();

                r.plots = region.getPlots();

                r.set = regSet;

                r.outline = false;

                list.add(r);
            }

            StoredData f = new StoredData();

            f.borderRGB = faction.getBorderColor().asRGB();
            f.fillRGB = faction.getFillColor().asRGB();
            f.fillO = faction.getFillOpacity();

            f.id = faction.getId();
            f.clazz = faction.getClass().getName();
            f.desc = faction.getDesc();

            f.plots = faction.getPlots();

            f.set = facSet;

            f.outline = true;

            list.add(f);
        }

        Bukkit.getScheduler().runTaskAsynchronously(Factionals.getFactionals(), () -> {
            Map<String, AreaMarker> newmap = new HashMap<>();

            for(World world : Bukkit.getWorlds()) {
                for (StoredData data : list) {
                    addToMap(data.set, data, world, data.outline, newmap);
                }
            }

            for(AreaMarker marker : markers.values()) {
                marker.deleteMarker();
            }

            markers = newmap;
        });
    }

    public void runMicro() {
        for(Marker marker : lotSet.getMarkers()) {
            marker.deleteMarker();
        }

        for(Marker marker : twnSet.getMarkers()) {
            marker.deleteMarker();
        }

        for (World world : Bukkit.getWorlds()) {
            for (Faction faction : Factionals.getFactionals().getRegistry(Faction.class).list()) {
                for (Region region : faction.getRegions()) {
                    Map<Integer, List<Location>> lotsAreas = new HashMap<>();

                    for (Integer lot : region.getLots().keySet()) {
                        lotsAreas.put(lot, new ArrayList<>());
                    }

                    for (LocationStorage area : region.getLotsLocations()) {
                        lotsAreas.get(region.getLot(area).getId()).add(new Location(world, area.x, 0, area.z));
                    }

                    for (Lot lot : region.getLots().values()) {
                        Bukkit.getScheduler().runTaskAsynchronously(Factionals.getFactionals(), () -> {
                            addToMapLot(lotSet, lotsAreas.get(lot.getId()), world, "LotImpl", "lot-" + lot.getId() + "-" + region.getId(), lot.getType().equals(PlotType.WILDERNESS));
                        });
                    }

                    for (Town town : region.getTowns()) {
                        List<Location> townLocations = new ArrayList<>();

                        for (Lot lot : town.getPlots()) {
                            townLocations.addAll(lotsAreas.get(lot.getId()));
                        }
                        Bukkit.getScheduler().runTaskAsynchronously(Factionals.getFactionals(), () -> {
                            addToMapLot(twnSet, townLocations, world, "TownImpl", town.getId(), false);
                        });
                    }
                }
            }
        }
    }

    enum direction { XPLUS, ZPLUS, XMINUS, ZMINUS }

    private int floodFillTarget(TileFlags src, TileFlags dest, int x, int y) {
        int cnt = 0;
        ArrayDeque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[] { x, y });

        while(!stack.isEmpty()) {
            int[] nxt = stack.pop();
            x = nxt[0];
            y = nxt[1];
            if(src.getFlag(x, y)) { /* Set in src */
                src.setFlag(x, y, false);   /* Clear source */
                dest.setFlag(x, y, true);   /* Set in destination */
                cnt++;
                if(src.getFlag(x+1, y))
                    stack.push(new int[] { x+1, y });
                if(src.getFlag(x-1, y))
                    stack.push(new int[] { x-1, y });
                if(src.getFlag(x, y+1))
                    stack.push(new int[] { x, y+1 });
                if(src.getFlag(x, y-1))
                    stack.push(new int[] { x, y-1 });
            }
        }
        return cnt;
    }

    private void addToMap(MarkerSet set, StoredData admin, World world, boolean outline, Map<String, AreaMarker> newmap) {
        double[] x;
        double[] z;
        int poly_index = 0; /* Index of polygon for given faction */

        Collection<Plot> blocks = new ArrayList<>(admin.plots);

        LinkedList<Plot> nodevals = new LinkedList<>();
        TileFlags curblks = new TileFlags();
        /* Loop through blocks: set flags on blockmaps */
        for (Plot b : blocks) {
            if(Plots.getW(b.getLocationId()) == Plots.getWorldId(world)) {
                curblks.setFlag(Plots.getX(b.getLocationId()), Plots.getZ(b.getLocationId()), true); /* Set flag for block */
                nodevals.addLast(b);
            }
        }

        /* Loop through until we don't find more areas */
        while (nodevals != null) {
            LinkedList<Plot> ournodes = null;
            LinkedList<Plot> newlist = null;
            TileFlags ourblks = null;
            int minx = Integer.MAX_VALUE;
            int minz = Integer.MAX_VALUE;
            for (Plot node : nodevals) {
                int nodex = Plots.getX(node.getLocationId());
                int nodez = Plots.getZ(node.getLocationId());
                /* If we need to start shape, and this block is not part of one yet */
                if ((ourblks == null) && curblks.getFlag(nodex, nodez)) {
                    ourblks = new TileFlags();  /* Create map for shape */
                    ournodes = new LinkedList<>();
                    floodFillTarget(curblks, ourblks, nodex, nodez);   /* Copy shape */
                    ournodes.add(node); /* Add it to our node list */
                    minx = nodex;
                    minz = nodez;
                }
                /* If shape found, and we're in it, add to our node list */
                else if ((ourblks != null) && ourblks.getFlag(nodex, nodez)) {
                    ournodes.add(node);
                    if (nodex < minx) {
                        minx = nodex;
                        minz = nodez;
                    } else if ((nodex == minx) && (nodez < minz)) {
                        minz = nodez;
                    }
                } else {  /* Else, keep it in the list for the next polygon */
                    if (newlist == null) newlist = new LinkedList<>();
                    newlist.add(node);
                }
            }
            nodevals = newlist; /* Replace list (null if no more to process) */
            if (ourblks != null) {
                /* Trace outline of blocks - start from minx, minz going to x+ */
                int init_x = minx;
                int init_z = minz;
                int cur_x = minx;
                int cur_z = minz;
                direction dir = direction.XPLUS;
                ArrayList<int[]> linelist = new ArrayList<>();
                linelist.add(new int[]{init_x, init_z}); // Add start point
                while ((cur_x != init_x) || (cur_z != init_z) || (dir != direction.ZMINUS)) {
                    switch (dir) {
                        case XPLUS: /* Segment in X+ direction */
                            if (!ourblks.getFlag(cur_x + 1, cur_z)) { /* Right turn? */
                                linelist.add(new int[]{cur_x + 1, cur_z}); /* Finish line */
                                dir = direction.ZPLUS;  /* Change direction */
                            } else if (!ourblks.getFlag(cur_x + 1, cur_z - 1)) {  /* Straight? */
                                cur_x++;
                            } else {  /* Left turn */
                                linelist.add(new int[]{cur_x + 1, cur_z}); /* Finish line */
                                dir = direction.ZMINUS;
                                cur_x++;
                                cur_z--;
                            }
                            break;
                        case ZPLUS: /* Segment in Z+ direction */
                            if (!ourblks.getFlag(cur_x, cur_z + 1)) { /* Right turn? */
                                linelist.add(new int[]{cur_x + 1, cur_z + 1}); /* Finish line */
                                dir = direction.XMINUS;  /* Change direction */
                            } else if (!ourblks.getFlag(cur_x + 1, cur_z + 1)) {  /* Straight? */
                                cur_z++;
                            } else {  /* Left turn */
                                linelist.add(new int[]{cur_x + 1, cur_z + 1}); /* Finish line */
                                dir = direction.XPLUS;
                                cur_x++;
                                cur_z++;
                            }
                            break;
                        case XMINUS: /* Segment in X- direction */
                            if (!ourblks.getFlag(cur_x - 1, cur_z)) { /* Right turn? */
                                linelist.add(new int[]{cur_x, cur_z + 1}); /* Finish line */
                                dir = direction.ZMINUS;  /* Change direction */
                            } else if (!ourblks.getFlag(cur_x - 1, cur_z + 1)) {  /* Straight? */
                                cur_x--;
                            } else {  /* Left turn */
                                linelist.add(new int[]{cur_x, cur_z + 1}); /* Finish line */
                                dir = direction.ZPLUS;
                                cur_x--;
                                cur_z++;
                            }
                            break;
                        case ZMINUS: /* Segment in Z- direction */
                            if (!ourblks.getFlag(cur_x, cur_z - 1)) { /* Right turn? */
                                linelist.add(new int[]{cur_x, cur_z}); /* Finish line */
                                dir = direction.XPLUS;  /* Change direction */
                            } else if (!ourblks.getFlag(cur_x - 1, cur_z - 1)) {  /* Straight? */
                                cur_z--;
                            } else {  /* Left turn */
                                linelist.add(new int[]{cur_x, cur_z}); /* Finish line */
                                dir = direction.XMINUS;
                                cur_x--;
                                cur_z--;
                            }
                            break;
                    }
                }
                /* Build information for specific area */
                String polyid = admin.clazz + "__" + admin.id + "__" + world + "__" + poly_index + "__" ;
                int sz = linelist.size();
                x = new double[sz];
                z = new double[sz];
                for (int i = 0; i < sz; i++) {
                    int[] line = linelist.get(i);
                    x[i] = (double) line[0] * 16.0;
                    z[i] = (double) line[1] * 16.0;
                }

                AreaMarker m = markers.remove(polyid);

                if(m == null) {
                    m = set.createAreaMarker(polyid, admin.desc, false, world.getName(), x, z, false);
                }

                if(m == null) {
                    return;
                }

                newmap.put(polyid, m);

                {m.setCornerLocations(x, z);

                m.setDescription(admin.desc);

                m.setLineStyle(1, 1, admin.borderRGB);

                m.setFillStyle(admin.fillO, admin.fillRGB);}

                if(outline) {
                    AreaMarker m2 = markers.remove(polyid + "__out");

                    if(m2 == null) {
                        m2 = outSet.createAreaMarker(polyid + "__out", admin.desc, false, world.getName(), x, z, false);
                    }

                    if(m2 == null) {
                        return;
                    }

                    newmap.put(polyid + "__out", m2);

                    m2.setCornerLocations(x, z);

                    m2.setDescription(admin.desc);

                    m2.setFillStyle(0, 0);
                    m2.setLineStyle(3, 1, admin.borderRGB);
                }


                /* Add to map */
                poly_index++;
            }
        }


    }

    private void addToMapLot(MarkerSet set, List<Location> locations, World world, String clazz, String id, boolean wild) {
        double[] x;
        double[] z;
        int poly_index = 0; /* Index of polygon for given faction */

        LinkedList<Location> nodevals = new LinkedList<>();
        TileFlags curblks = new TileFlags();
        /* Loop through blocks: set flags on blockmaps */
        for (Location location : locations) {
            if (Plots.getWorldId(location.getWorld()) == Plots.getWorldId(world)) {
                curblks.setFlag(location.getBlockX(), location.getBlockZ(), true); /* Set flag for block */
                nodevals.addLast(location);
            }
        }

        /* Loop through until we don't find more areas */
        while (nodevals != null) {
            LinkedList<Location> ournodes = null;
            LinkedList<Location> newlist = null;
            TileFlags ourblks = null;
            int minx = Integer.MAX_VALUE;
            int minz = Integer.MAX_VALUE;
            for (Location node : nodevals) {
                int nodex = node.getBlockX();
                int nodez = node.getBlockZ();
                /* If we need to start shape, and this block is not part of one yet */
                if ((ourblks == null) && curblks.getFlag(nodex, nodez)) {
                    ourblks = new TileFlags();  /* Create map for shape */
                    ournodes = new LinkedList<>();
                    floodFillTarget(curblks, ourblks, nodex, nodez);   /* Copy shape */
                    ournodes.add(node); /* Add it to our node list */
                    minx = nodex;
                    minz = nodez;
                }
                /* If shape found, and we're in it, add to our node list */
                else if ((ourblks != null) && ourblks.getFlag(nodex, nodez)) {
                    ournodes.add(node);
                    if (nodex < minx) {
                        minx = nodex;
                        minz = nodez;
                    } else if ((nodex == minx) && (nodez < minz)) {
                        minz = nodez;
                    }
                } else {  /* Else, keep it in the list for the next polygon */
                    if (newlist == null) newlist = new LinkedList<>();
                    newlist.add(node);
                }
            }
            nodevals = newlist; /* Replace list (null if no more to process) */
            if (ourblks != null) {
                /* Trace outline of blocks - start from minx, minz going to x+ */
                int init_x = minx;
                int init_z = minz;
                int cur_x = minx;
                int cur_z = minz;
                direction dir = direction.XPLUS;
                ArrayList<int[]> linelist = new ArrayList<>();
                linelist.add(new int[]{init_x, init_z}); // Add start point
                while ((cur_x != init_x) || (cur_z != init_z) || (dir != direction.ZMINUS)) {
                    switch (dir) {
                        case XPLUS: /* Segment in X+ direction */
                            if (!ourblks.getFlag(cur_x + 1, cur_z)) { /* Right turn? */
                                linelist.add(new int[]{cur_x + 1, cur_z}); /* Finish line */
                                dir = direction.ZPLUS;  /* Change direction */
                            } else if (!ourblks.getFlag(cur_x + 1, cur_z - 1)) {  /* Straight? */
                                cur_x++;
                            } else {  /* Left turn */
                                linelist.add(new int[]{cur_x + 1, cur_z}); /* Finish line */
                                dir = direction.ZMINUS;
                                cur_x++;
                                cur_z--;
                            }
                            break;
                        case ZPLUS: /* Segment in Z+ direction */
                            if (!ourblks.getFlag(cur_x, cur_z + 1)) { /* Right turn? */
                                linelist.add(new int[]{cur_x + 1, cur_z + 1}); /* Finish line */
                                dir = direction.XMINUS;  /* Change direction */
                            } else if (!ourblks.getFlag(cur_x + 1, cur_z + 1)) {  /* Straight? */
                                cur_z++;
                            } else {  /* Left turn */
                                linelist.add(new int[]{cur_x + 1, cur_z + 1}); /* Finish line */
                                dir = direction.XPLUS;
                                cur_x++;
                                cur_z++;
                            }
                            break;
                        case XMINUS: /* Segment in X- direction */
                            if (!ourblks.getFlag(cur_x - 1, cur_z)) { /* Right turn? */
                                linelist.add(new int[]{cur_x, cur_z + 1}); /* Finish line */
                                dir = direction.ZMINUS;  /* Change direction */
                            } else if (!ourblks.getFlag(cur_x - 1, cur_z + 1)) {  /* Straight? */
                                cur_x--;
                            } else {  /* Left turn */
                                linelist.add(new int[]{cur_x, cur_z + 1}); /* Finish line */
                                dir = direction.ZPLUS;
                                cur_x--;
                                cur_z++;
                            }
                            break;
                        case ZMINUS: /* Segment in Z- direction */
                            if (!ourblks.getFlag(cur_x, cur_z - 1)) { /* Right turn? */
                                linelist.add(new int[]{cur_x, cur_z}); /* Finish line */
                                dir = direction.XPLUS;  /* Change direction */
                            } else if (!ourblks.getFlag(cur_x - 1, cur_z - 1)) {  /* Straight? */
                                cur_z--;
                            } else {  /* Left turn */
                                linelist.add(new int[]{cur_x, cur_z}); /* Finish line */
                                dir = direction.XMINUS;
                                cur_x--;
                                cur_z--;
                            }
                            break;
                    }
                }
                /* Build information for specific area */
                String polyid = clazz + "__" + id + "__" + world + "__" + poly_index + "__";
                int sz = linelist.size();
                x = new double[sz];
                z = new double[sz];
                for (int i = 0; i < sz; i++) {
                    int[] line = linelist.get(i);
                    x[i] = line[0];
                    z[i] = line[1];
                }

                AreaMarker m = set.createAreaMarker(polyid, "bruh", false, world.getName(), x, z, false);

                if(m == null) {
                    return;
                }

                if(wild) {
                    /* Set line and fill properties */
                    m.setLineStyle(1, 1, 0x007F00);
                    m.setFillStyle(0.1, 0x007F00);
                } else {
                    m.setLineStyle(1, 1, 16777215);
                    m.setFillStyle(0.1, 16777215);
                }



                /* Add to map */
                poly_index++;
            }
        }
    }

    static class StoredData {
        Collection<Plot> plots;

        double fillO;

        int fillRGB;
        int borderRGB;

        String id;
        String clazz;
        String desc;

        MarkerSet set;

        boolean outline;
    }
}
