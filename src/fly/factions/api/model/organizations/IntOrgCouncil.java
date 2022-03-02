package fly.factions.api.model.organizations;

import fly.factions.api.model.Faction;
import fly.factions.api.model.FactionComponent;
import fly.factions.api.model.User;

import java.util.List;

public interface IntOrgCouncil extends IntOrgComponent {
    List<IntOrgLevel> getLevels();

    int getRepresentatives(FactionComponent faction);

    List<User> getRepresentatives();

    List<User> getRepresentatives(Faction faction);

    void setRepresentatives(Faction faction, List<User> representatives);
}

