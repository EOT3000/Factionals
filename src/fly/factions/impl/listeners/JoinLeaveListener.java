package fly.factions.impl.listeners;

import fly.factions.impl.model.UserImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinLeaveListener extends ListenerImpl {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(getUserFromPlayer(event.getPlayer()) == null) {
            addUser(new UserImpl(event.getPlayer().getUniqueId(), event.getPlayer().getDisplayName()));
        }
    }
}
