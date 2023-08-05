package me.fly.factions.api.model;

public interface Plot extends Savable, LandDivision {
    void setFaction(Faction faction);

    int getLocationId();

    Faction getFaction();

    LandAdministrator getAdministrator();

    void setAdministrator(LandAdministrator administrator);
}
