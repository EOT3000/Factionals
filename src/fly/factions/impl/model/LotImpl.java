package fly.factions.impl.model;

import fly.factions.api.model.*;
import fly.factions.api.permissions.PlotPermission;
import fly.factions.impl.commands.faction.plot.set.lot.PlotSetLotCommand;
import org.bukkit.World;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

public class LotImpl implements Lot {
    private PlotOwner owner;
    private int price;
    private Region region;
    private int id;
    private World world;

    private EnumMap<PlotPermission, Set<Permissible>> permissionMap = new EnumMap<>(PlotPermission.class);
    private boolean publicLot;

    private Town town;

    private int xP;
    private int zP;

    private int xP2;
    private int zP2;

    private int xS;
    private int zS;

    private int xS2;
    private int zS2;

    private int zT;
    private int xT;

    private int zT2;
    private int xT2;

    private int level = 0;

    private PlotType type = PlotType.DEFAULT;

    //private List<Pair<Integer, Integer>> blocks = new ArrayList<>();

    public LotImpl(Region region, int id, World world) {
        this.region = region;

        this.owner = region;

        for(PlotPermission permission : PlotPermission.values()) {
            permissionMap.put(permission, new HashSet<>());
        }

        this.world = world;
        this.id = id;
    }

    @Override
    public World getWorld() {
        return world;
    }

    //@Override
    //public List<Pair<Integer, Integer>> getBlocks() {
    //    return new ArrayList<>(blocks);
    //}

    @Override
    public boolean hasPermission(User user, PlotPermission permission) {
        for(Permissible permissible : permissionMap.get(permission)) {

            if(permissible.userHasPlotPermissions(user, false, publicLot)) {
                return true;
            }
        }

        return owner.userHasPlotPermissions(user, false, publicLot);
    }

    @Override
    public void setPermission(Permissible permissible, PlotPermission permission, boolean allowed) {
        if(allowed) {
            permissionMap.get(permission).add(permissible);
        } else {
            permissionMap.get(permission).remove(permissible);
        }
    }

    @Override
    public EnumMap<PlotPermission, Set<Permissible>> getPermissions() {
        EnumMap<PlotPermission, Set<Permissible>> ret = new EnumMap<>(PlotPermission.class);

        for(PlotPermission permission : PlotPermission.values()) {
            ret.put(permission, new HashSet<>(permissionMap.get(permission)));
        }

        return ret;
    }

    @Override
    public PlotOwner getOwner() {
        return owner;
    }

    @Override
    public void setOwner(PlotOwner owner) {
        this.owner = owner;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public Town getTown() {
        return town;
    }

    @Override
    public void setTown(Town town) {
        if(town.getRegion().equals(region)) {
            if(this.town != null) {
                this.town.removePlot(this);
            }

            if(owner == region) {
                owner = town;
            }

            this.town = town;
            town.addPlot(this);
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Region getRegion() {
        return region;
    }

    @Override
    public int getXP() {
        return xP;
    }

    @Override
    public int getZP() {
        return zP;
    }

    @Override
    public int getXS() {
        return xS;
    }

    @Override
    public int getZS() {
        return zS;
    }

    @Override
    public int getXT() {
        return xT;
    }

    @Override
    public int getZT() {
        return zT;
    }

    @Override
    public void setXP(int x) {
        this.xP = x;
    }

    @Override
    public void setZP(int z) {
        this.zP = z;
    }

    @Override
    public void setXS(int x) {
        this.xS = x;
    }

    @Override
    public void setZS(int z) {
        this.zS = z;
    }

    @Override
    public void setXT(int x) {
        this.xT = x;
    }

    @Override
    public void setZT(int z) {
        this.zT = z;
    }

    @Override
    public int getXP2() {
        return xP2;
    }

    @Override
    public int getZP2() {
        return zP2;
    }

    @Override
    public int getXS2() {
        return xS2;
    }

    @Override
    public int getZS2() {
        return zS2;
    }

    @Override
    public int getXT2() {
        return xT2;
    }

    @Override
    public int getZT2() {
        return zT2;
    }

    @Override
    public void setXP2(int x) {
        this.xP2 = x;
    }

    @Override
    public void setZP2(int z) {
        this.zP2 = z;
    }

    @Override
    public void setXS2(int x) {
        this.xS2 = x;
    }

    @Override
    public void setZS2(int z) {
        this.zS2 = z;
    }

    @Override
    public void setXT2(int x) {
        this.xT2 = x;
    }

    @Override
    public void setZT2(int z) {
        this.zT2 = z;
    }

    @Override
    public void registerChange(int level) {
        this.level = Math.max(this.level, level);
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void resetBorders() {
        if (level >= 1) {
            region.setLotsAndValidate(world, xP, zP, xP2, zP2, Integer.MAX_VALUE - 1, Integer.MAX_VALUE - 1, Integer.MAX_VALUE - 1, Integer.MAX_VALUE - 1, this, 1);
        }
        if (level >= 2) {
            region.setLotsAndValidate(world, xS, zS, xS2, zS2, Integer.MAX_VALUE - 1, Integer.MAX_VALUE - 1, Integer.MAX_VALUE - 1, Integer.MAX_VALUE - 1, this, 2);
        }
        if (level >= 3) {
            region.setLotsAndValidate(world, xT, zT, xT2, zT2, Integer.MAX_VALUE - 1, Integer.MAX_VALUE - 1, Integer.MAX_VALUE - 1, Integer.MAX_VALUE - 1, this, 3);
        }
    }

    @Override
    public Faction getFaction() {
        return region.getFaction();
    }

    @Override
    public PlotType getType() {
        return type;
    }

    @Override
    public void setType(PlotType type) {
        this.type = type;
    }

    @Override
    public void delete() {
        region.removeLot(this);
    }
}