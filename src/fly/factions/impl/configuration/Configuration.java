package fly.factions.impl.configuration;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    //TODO: configuration

    private final Map<Integer, Integer> powerPerPlayer = new HashMap<>();

    public Configuration() {
        powerPerPlayer.put(1, 6000);
        powerPerPlayer.put(2, 6250);
        powerPerPlayer.put(3, 6500);
        powerPerPlayer.put(4, 6750);
        powerPerPlayer.put(5, 7250);
        powerPerPlayer.put(6, 7750);
        powerPerPlayer.put(7, 8500);
        powerPerPlayer.put(8, 9250);
        powerPerPlayer.put(9, 10000);
        powerPerPlayer.put(10, 11000);
        powerPerPlayer.put(11, 12000);
        powerPerPlayer.put(12, 13000);
        powerPerPlayer.put(13, 14000);
        powerPerPlayer.put(14, 15000);

        powerPerPlayer.put(0, 15000);
    }

    public int getPowerForPlayer(int number) {
        return powerPerPlayer.getOrDefault(number, powerPerPlayer.get(0));
    }
}
