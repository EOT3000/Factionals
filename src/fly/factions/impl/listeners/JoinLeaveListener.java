package fly.factions.impl.listeners;

import fly.factions.api.model.User;
import fly.factions.impl.model.UserImpl;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinLeaveListener extends ListenerImpl {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        User user = getUserFromPlayer(event.getPlayer());

        if(user == null) {
            user = addUser(new UserImpl(event.getPlayer().getUniqueId(), event.getPlayer().getDisplayName()));
        } else {
            getUserFromPlayer(event.getPlayer()).updateName();
        }

        user.setAutoClaiming(false);
    }
}
