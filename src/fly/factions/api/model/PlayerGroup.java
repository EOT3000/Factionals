package fly.factions.api.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface PlayerGroup {
    void broadcast(String s);

    Set<User> getMembers();
}
