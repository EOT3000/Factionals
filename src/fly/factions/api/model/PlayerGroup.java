package fly.factions.api.model;

import java.util.Collection;

public interface PlayerGroup {
    void broadcast(String s);

    Collection<User> getMembers();
}
