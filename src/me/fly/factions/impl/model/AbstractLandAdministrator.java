package me.fly.factions.impl.model;

import me.fly.factions.api.model.LandAdministrator;
import me.fly.factions.api.model.User;
import org.bukkit.Color;

import java.util.*;

public abstract class AbstractLandAdministrator<T> extends AbstractFactionComponent implements LandAdministrator<T> {
    protected Color fillColor = Color.fromRGB(255,255,255);
    protected double fillOpacity = 0.3;

    protected Color borderColor = Color.fromRGB(255,255,255);

    protected Set<T> plots = new HashSet<>();

    protected AbstractLandAdministrator(String name, User leader) {
        super(name, leader);
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    @Override
    public double getFillOpacity() {
        return fillOpacity;
    }

    @Override
    public void setFillOpacity(double d) {
        this.fillOpacity = d;
    }

    @Override
    public Color getBorderColor() {
        return borderColor;
    }

    @Override
    public void setBorderColor(Color color) {
        this.borderColor = color;
    }


    @Override
    public Collection<T> getPlots() {
        return new ArrayList<>(plots);
    }

    @Override
    public void addPlot(T plot) {
        plots.add(plot);

        getFaction().setRequiresUpdate(true);
    }

    @Override
    public void removePlot(T plot) {
        plots.remove(plot);

        getFaction().setRequiresUpdate(true);
    }
}
