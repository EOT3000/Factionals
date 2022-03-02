package fly.factions.impl.model;

import fly.factions.api.model.*;
import fly.factions.api.model.organizations.IntOrgCouncil;
import fly.factions.api.model.organizations.IntOrgLevel;
import fly.factions.api.model.organizations.InternationalOrganization;
import fly.factions.api.permissions.FactionPermission;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InternationalOrganizationImpl extends AbstractLandAdministrator<Plot> implements InternationalOrganization {
    private boolean deleted = false;
    private final long creationTime;
    private String description = "";

    private List<FactionComponent> members = new ArrayList<>();
    private List<IntOrgCouncil> councils = new ArrayList<>();
    private List<IntOrgLevel> levels = new ArrayList<>();


    public InternationalOrganizationImpl(String name, User leader) {
        this(name, leader, System.currentTimeMillis());
    }

    public InternationalOrganizationImpl(String name, User leader, long creationTime) {
        super(name, leader);

        this.creationTime = creationTime;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public Collection<Region> getRegions() {
        return new ArrayList<>();
    }

    @Override
    public Region getRegion(String s) {
        return null;
    }

    @Override
    public void addRegion(Region region) {

    }

    @Override
    public void removeRegion(Region region) {

    }

    @Override
    public void joinRegion(Faction faction, Region region) {

    }

    @Override
    public Collection<ExecutiveDivision> getDepartments() {
        return new ArrayList<>();
    }

    @Override
    public ExecutiveDivision getDepartment(String s) {
        return null;
    }

    @Override
    public void addDepartment(ExecutiveDivision division) {

    }

    @Override
    public void removeDepartment(ExecutiveDivision division) {

    }

    @Override
    public void delete() {
        this.deleted = true;
    }

    @Override
    public boolean hasPermission(User user, FactionPermission permission) {
        return false;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public int getMaxPower() {
        int p = 0;

        for(FactionComponent component : members) {
            if(component instanceof Faction) {
                p += ((Faction) component).getMaxPower()/34;
            }
        }

        return p;
    }

    @Override
    public int getPowerPerPlayer() {
        return 0;
    }

    @Override
    public int getCurrentPower() {
        int p = 0;

        for(FactionComponent component : members) {
            if(component instanceof Faction) {
                p += ((Faction) component).getCurrentPower()/34;
            }
        }

        return p;
    }

    @Override
    public void addInvite(Faction faction) {

    }

    @Override
    public boolean hasInviteFrom(Faction faction) {
        return false;
    }

    @Override
    public void setDescription(String string) {
        description = string;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setOpen(boolean w) {

    }

    @Override
    public boolean isOpen() {
        return false;
    }

    // Int Org Stuff

    @Override
    public InternationalOrganization getOrganization() {
        return this;
    }

    @Override
    public List<IntOrgCouncil> getCouncils() {
        return new ArrayList<>(councils);
    }

    @Override
    public List<IntOrgLevel> getLevels() {
        return new ArrayList<>(levels);
    }

    @Override
    public List<FactionComponent> getMemberOrganizations() {
        return new ArrayList<>(members);
    }

    @Override
    public void addCouncil(IntOrgCouncil council) {
        councils.add(council);
    }

    @Override
    public void addLevel(IntOrgLevel level) {
        levels.add(level);
    }

    @Override
    public void addMemberOrganization(FactionComponent component) {
        members.add(component);
    }

    @Override
    public void removeCouncil(IntOrgCouncil council) {
        councils.remove(council);
    }

    @Override
    public void removeLevel(IntOrgLevel level) {
        levels.remove(level);
    }

    @Override
    public void removeMemberOrganization(FactionComponent component) {
        members.remove(component);
    }

    @Override
    public boolean isLevel(Faction faction, IntOrgLevel level) {
        return true;
    }

    // Misc and Dynmap

    @Override
    public Faction getFaction() {
        return this;
    }

    @Override
    public String getDesc() {
        return "no";
    }

    @Override
    public double getBorderOpacity() {
        return 0;
    }

    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public boolean userHasPlotPermissions(User user, boolean owner, boolean pub) {
        return false;
    }

    // IDs and Names

    @Override
    public void setName(String name) {

    }

    @Override
    public String getFormattedName() {
        return "International Organization " + name;
    }

    @Override
    public String getId() {
        return "int-org-" + name;
    }

    // Users

    @Override
    public void removeMember(User user) {

    }
}
