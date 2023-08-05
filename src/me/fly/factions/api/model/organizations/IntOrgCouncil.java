package me.fly.factions.api.model.organizations;

import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.FactionComponent;
import me.fly.factions.api.model.User;

import java.util.List;

public interface IntOrgCouncil extends IntOrgComponent {
    List<IntOrgLevel> getLevels();

    int getRepresentatives(FactionComponent faction);

    List<User> getRepresentatives();

    List<User> getRepresentatives(Faction faction);

    void setRepresentatives(Faction faction, List<User> representatives);
}

