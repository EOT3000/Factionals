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
        powerPerPlayer.put(8, 9200);
        powerPerPlayer.put(9, 11550);
        powerPerPlayer.put(10, 12000);
        powerPerPlayer.put(11, 13400);
        powerPerPlayer.put(12, 14050);
        powerPerPlayer.put(13, 15600);
        powerPerPlayer.put(14, 16600);
        powerPerPlayer.put(15, 17600);
        powerPerPlayer.put(16, 18600);
        powerPerPlayer.put(17, 19600);
        powerPerPlayer.put(18, 20600);
        powerPerPlayer.put(19, 21600);
        powerPerPlayer.put(20, 22600);

        powerPerPlayer.put(0, 23600);
    }

    public int getPowerForPlayer(int number) {
        return powerPerPlayer.getOrDefault(number, powerPerPlayer.get(0));
    }
}
