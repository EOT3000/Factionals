package me.fly.factions.api.model.organizations;

import me.fly.factions.api.model.FactionComponent;

import java.util.List;

public interface IntOrgLevel extends IntOrgComponent {
    List<FactionComponent> getMemberOrganizations();

    void removeMemberOrganization(FactionComponent component);

    void addMemberOrganization(FactionComponent component);
}
