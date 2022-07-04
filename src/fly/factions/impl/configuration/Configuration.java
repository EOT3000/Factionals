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
        powerPerPlayer.put(1, 16000);
        powerPerPlayer.put(2, 16500);
        powerPerPlayer.put(3, 17000);
        powerPerPlayer.put(4, 18000);
        powerPerPlayer.put(5, 19000);
        powerPerPlayer.put(6, 20500);
        powerPerPlayer.put(7, 22000);
        powerPerPlayer.put(8, 23000);
        powerPerPlayer.put(9, 23500);
        powerPerPlayer.put(10, 24000);
        powerPerPlayer.put(11, 23000);
        powerPerPlayer.put(12, 22000);
        powerPerPlayer.put(13, 20000);
        powerPerPlayer.put(14, 18000);
        powerPerPlayer.put(15, 15000);
        powerPerPlayer.put(16, 12000);
        powerPerPlayer.put(17, 10000);
        powerPerPlayer.put(18, 9000);
        powerPerPlayer.put(19, 8500);
        powerPerPlayer.put(20, 6250);
        powerPerPlayer.put(21, 6000);
        powerPerPlayer.put(22, 5000);
        powerPerPlayer.put(0, 3000);

        //powerPerPlayer.put(0, 23600);
    }

    public int getPowerForPlayer(int number) {
        return powerPerPlayer.getOrDefault(number, powerPerPlayer.get(0));
    }
}
