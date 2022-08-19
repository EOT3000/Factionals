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
            System.out.println(entry.getKey() + ": " + (total+=entry.getValue()));
        }
    }

    //TODO: configuration

    private final Map<Integer, Integer> powerPerPlayer = new HashMap<>();

    public Configuration() {
        powerPerPlayer.put(0, 10000);

        //powerPerPlayer.put(0, 23600);
    }

    public int getPowerForPlayer(int number) {
        return powerPerPlayer.getOrDefault(number, powerPerPlayer.get(0));
    }
}
