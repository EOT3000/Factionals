package fly.factions.impl.listeners;

import fly.factions.api.model.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener extends ListenerImpl {
    @EventHandler
    public void onPlayerDie(PlayerDeathEvent event) {
        User user = getUserFromPlayer(event.getPlayer());

        user.setPower(user.getPower()-1000);
    }
}
