package me.fly.factions.impl.model;

import me.fly.factions.api.exceptions.NotAMemberException;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.organizations.Organization;
import me.fly.factions.api.model.User;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class OrganizationImpl extends AbstractFactionComponent implements Organization {
    public OrganizationImpl(String name, User leader) {
        super(name, leader);
    }

    @Override
    public void setLeader(User leader) {
        if(!members.contains(leader)) {
            throw new NotAMemberException();
        }

        this.leader = leader;
    }

    @Override
    public void removeMember(User user) {
        if(user.equals(leader)) {
            return;
        }

        members.remove(user);
    }

    @Override
    public String getId() {
        return "organization-" + name;
    }

    @Override
    public boolean userHasPlotPermissions(User user, boolean owner, boolean pub) {
        return members.contains(user);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.AIR);
    }

    @Override
    public Faction getFaction() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getFormattedName() {
        return "Organization " + name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
