package me.fly.factions.api.model;

import me.fly.factions.api.permissions.PlotPermission;
import org.bukkit.World;

import java.util.EnumMap;
import java.util.Set;

public interface Lot extends LandDivision {
    World getWorld();

    void setPermission(Permissible permissible, PlotPermission permission, boolean allowed);

    EnumMap<PlotPermission, Set<Permissible>> getPermissions();

    PlotOwner getOwner();

    void setOwner(PlotOwner owner);

    void setPrice(int price);

    int getPrice();

    Town getTown();

    void setTown(Town town);

    int getId();

    Region getRegion();

    int getXP();
    int getZP();

    int getXS();
    int getZS();

    int getXT();
    int getZT();

    void setXP(int x);
    void setZP(int z);

    void setXS(int x);
    void setZS(int z);

    void setXT(int x);
    void setZT(int z);

    int getXP2();
    int getZP2();

    int getXS2();
    int getZS2();

    int getXT2();
    int getZT2();

    void setXP2(int x);
    void setZP2(int z);

    void setXS2(int x);
    void setZS2(int z);

    void setXT2(int x);
    void setZT2(int z);

    void registerChange(int level);

    int getLevel();

    void resetBorders();

    PlotType getType();

    void setType(PlotType type);

    void delete();
}
