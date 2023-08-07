package me.fly.factions.api.model;

import me.fly.factions.api.claiming.ClaimType;
import org.bukkit.Color;

import java.util.Collection;

public interface LandAdministrator<T> extends FactionComponent, ClaimType {
    Collection<T> getPlots();

    void addPlot(T plot);
    void removePlot(T plot);

    String getDesc();

    Color getFillColor();
    void setFillColor(Color color);

    double getFillOpacity();
    void setFillOpacity(double d);

    Color getBorderColor();
    void setBorderColor(Color color);

    double getBorderOpacity();

    Faction getFaction();
}
