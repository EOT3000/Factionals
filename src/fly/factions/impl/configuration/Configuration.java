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
        powerPerPlayer.put(1, 12000);
        powerPerPlayer.put(2, 12500);
        powerPerPlayer.put(3, 13000);
        powerPerPlayer.put(4, 14000);
        powerPerPlayer.put(5, 15000);
        powerPerPlayer.put(6, 16500);
        powerPerPlayer.put(7, 18000);
        powerPerPlayer.put(8, 19000);
        powerPerPlayer.put(9, 19500);
        powerPerPlayer.put(10, 20000);
        powerPerPlayer.put(11, 19000);
        powerPerPlayer.put(12, 18000);
        powerPerPlayer.put(13, 16000);
        powerPerPlayer.put(14, 14000);
        powerPerPlayer.put(15, 11000);
        powerPerPlayer.put(16, 8000);
        powerPerPlayer.put(17, 6000);
        powerPerPlayer.put(18, 5000);
        powerPerPlayer.put(19, 4500);
        powerPerPlayer.put(20, 2250);
        powerPerPlayer.put(0, 2000);

        //powerPerPlayer.put(0, 23600);
    }

    public int getPowerForPlayer(int number) {
        return powerPerPlayer.getOrDefault(number, powerPerPlayer.get(0));
    }
}
