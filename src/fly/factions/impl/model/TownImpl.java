package fly.factions.impl.model;

import fly.factions.api.model.*;
import fly.factions.api.permissions.Permissibles;
import org.bukkit.inventory.ItemStack;

public class TownImpl extends AbstractLandAdministrator<Lot> implements Town {
    private Region region;

    public TownImpl(String name, User leader, Region region) {
        super(name, leader);

        this.region = region;

        Permissibles.add(region.getName() + ":" + name, this);
        Permissibles.add(getId(), this);
    }

    @Override
    public void removeMember(User user) {
        members.remove(user);
    }

    @Override
    public String getId() {
        return region.getId() + "-town-" + name;
    }

    @Override
    public boolean userHasPlotPermissions(User user, boolean owner, boolean pub) {
        return (!owner && members.contains(user)) || leader.equals(user);
    }

    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public Region getRegion() {
        return region;
    }

    @Override
    public String getDesc() {
        return "bruh";
    }

    @Override
    public double getBorderOpacity() {
        return 1;
    }

    @Override
    public Faction getFaction() {
        return region.getFaction();
    }

    @Override
    public String getFormattedName() {
        return "Town of " + name + ", " + region.getName() + ", " + region.getFaction().getName();
    }

    @Override
    public void setName(String name) {
        this.name = name;

        Permissibles.remove(this);

        Permissibles.add(region.getName() + ":" + name, this);
        Permissibles.add(getId(), this);
    }
}
