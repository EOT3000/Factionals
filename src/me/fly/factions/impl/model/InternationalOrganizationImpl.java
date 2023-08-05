package me.fly.factions.impl.model;

import me.fly.factions.Factionals;
import me.fly.factions.api.model.*;
import me.fly.factions.api.model.organizations.InternationalOrganization;
import me.fly.factions.api.model.organizations.Organization;
import me.fly.factions.api.permissions.FactionPermission;
import me.fly.factions.api.permissions.Permissibles;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InternationalOrganizationImpl extends AbstractLandAdministrator<Plot> implements InternationalOrganization {
    private boolean deleted = false;
    private final long creationTime;
    private String description = "";

    private List<FactionComponent> members = new ArrayList<>();
    //private List<IntOrgCouncil> councils = new ArrayList<>();
    //private List<IntOrgLevel> levels = new ArrayList<>();

    private List<FactionComponent> invites = new ArrayList<>();


    public InternationalOrganizationImpl(String name, User leader) {
        this(name, leader, System.currentTimeMillis());
    }

    public InternationalOrganizationImpl(String name, User leader, long creationTime) {
        super(name, leader);

        this.creationTime = creationTime;

        Factionals.getFactionals().getRegistry(Organization.class, String.class).set(name, this);

        Permissibles.add(name, this);
        Permissibles.add(getId(), this);
        Permissibles.add("io:" + name, this);

        fillColor = Color.AQUA;
        borderColor = Color.BLUE;
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
        return user.equals(leader);
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
        invites.add(faction);
    }

    @Override
    public boolean hasInviteFrom(Faction faction) {
        return invites.contains(faction);
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
    public List<FactionComponent> getMemberOrganizations() {
        return new ArrayList<>(members);
    }

    @Override
    public void addMemberOrganization(FactionComponent component) {
        members.add(component);
    }

    @Override
    public void removeMemberOrganization(FactionComponent component) {
        members.remove(component);
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
        for(FactionComponent m : members) {
            if(m.getMembers().contains(user)) {
                return true;
            }
        }

        return leader.equals(user);
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

    @Override
    public boolean isAnyPlayerOnline() {
        for(FactionComponent faction : members) {
            for (User user : faction.getMembers()) {
                if (Bukkit.getOfflinePlayer(user.getUniqueId()).isOnline()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean requiresUpdate() {
        return false;
    }

    @Override
    public void setRequiresUpdate(boolean b) {

    }

    @Override
    public void inviteFaction() {

    }

    @Override
    public void cancelFactionInvite(Faction faction) {

    }

    @Override
    public boolean isInvited(Faction faction) {
        return false;
    }

    @Override
    public void broadcast(String s) {
        for(FactionComponent component : members) {
            for(User user : component.getMembers()) {
                user.sendMessage(s);
            }
        }
    }
}
