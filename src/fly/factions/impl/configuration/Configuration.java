package fly.factions.impl.configuration;

import fly.factions.impl.util.Plots;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(132997137));

        System.out.println(Plots.getX(132997137));
        System.out.println(Plots.getZ(132997137));
        System.out.println(Plots.getW(132997137));

        Configuration configuration = new Configuration();

        int total = 0;

        for(Map.Entry<Integer, Integer> entry : configuration.powerPerPlayer.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue()*entry.getKey());
        }
    }

    //TODO: configuration

    private final Map<Integer, Integer> powerPerPlayer = new HashMap<>();

    public Configuration() {
        powerPerPlayer.put(1, 18000);
        powerPerPlayer.put(2, 20000);
        powerPerPlayer.put(3, 22000);
        powerPerPlayer.put(4, 24000);
        powerPerPlayer.put(5, 24000);
        powerPerPlayer.put(6, 18000);
        powerPerPlayer.put(7, 12000);
        powerPerPlayer.put(8, 6000);
        powerPerPlayer.put(0, 1000);

        //powerPerPlayer.put(0, 23600);
    }

    public int getPowerForPlayer(int number) {
        return powerPerPlayer.getOrDefault(number, powerPerPlayer.get(0));
    }
}
