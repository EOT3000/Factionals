package me.fly.factions.api.model;

import me.fly.factions.api.permissions.PlotPermission;

public interface LandDivision {
    Faction getFaction();

    boolean hasPermission(User user, PlotPermission permission);
}
