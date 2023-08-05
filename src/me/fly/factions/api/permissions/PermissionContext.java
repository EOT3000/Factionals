package me.fly.factions.api.permissions;

import me.fly.factions.api.model.*;

public class PermissionContext {
    public static boolean canDoPlots(User user, LandAdministrator<?> admin, Lot lot, PlotPermission permission) {
        if(user.isAdminMode()) {
            return true;
        }

        if(lot == null) {
            return admin.userHasPlotPermissions(user, false, false);
        } else {
            return lot.hasPermission(user, permission);
        }
    }

    public static boolean canDoRegion(User user, Region region) {
        return region.getLeader().equals(user) || canDoFaction(user, region.getFaction(), FactionPermission.OWNER) || user.isAdminMode();
    }

    public static boolean canDoFaction(User user, Faction faction, FactionPermission permission) {
        return faction.hasPermission(user, permission) || user.isAdminMode();
    }

    //TODO
    public enum PermissionContextType {
        NONE,

        FACTION_LEADER,
        FACTION_GROUP_LEADER,
        FACTION_GROUP_MEMBER,
        FACTION_MEMBER,

        REGION_LEADER,
        REGION_GROUP_MEMBER,
        REGION_MEMBER,

        CORPORATION_LEADER,
        CORPORATION_MEMBER;
    }
}
