package me.fly.factions.api.model.organizations;

import me.fly.factions.api.model.Faction;

public interface InternationalOrganization extends Organization, Faction, IntOrgLevel {
/*    List<IntOrgCouncil> getCouncils();

    List<IntOrgLevel> getLevels();

    void addCouncil(IntOrgCouncil council);

    void addLevel(IntOrgLevel level);

    void removeCouncil(IntOrgCouncil council);

    void removeLevel(IntOrgLevel level);

    boolean isLevel(Faction faction, IntOrgLevel level);*/

    void inviteFaction();

    void cancelFactionInvite(Faction faction);

    boolean isInvited(Faction faction);

    //TODO: make this
}
