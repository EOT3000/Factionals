package me.fly.factions.integrations.slimefun;

import me.fly.factions.impl.listeners.SlimefunListener;
import org.bukkit.Bukkit;

public class SlimefunIntegration {
    private static boolean enabled = false;

    public static void checkAndDo() {
        if(!enabled) {
            enabled = Bukkit.getPluginManager().isPluginEnabled("Slimefun");

            if(enabled) {
                new SlimefunListener();
            }
        }
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
