package fly.factions.impl.configuration;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();

        int total = 0;

        for(Map.Entry<Integer, Integer> entry : configuration.powerPerPlayer.entrySet()) {
            total+=entry.getValue();

            //System.out.println("powerPerPlayer.put(" + entry.getKey() + ", " + entry.getValue()*6 + ");");
            System.out.println(total/entry.getKey());
        }
    }

    //TODO: configuration

    private final Map<Integer, Integer> powerPerPlayer = new HashMap<>();

    public Configuration() {
        powerPerPlayer.put(1, 36000);
        powerPerPlayer.put(2, 37500);
        powerPerPlayer.put(3, 39000);
        powerPerPlayer.put(4, 40500);
        powerPerPlayer.put(5, 43500);
        powerPerPlayer.put(6, 46500);
        powerPerPlayer.put(7, 51000);
        powerPerPlayer.put(8, 55200);
        powerPerPlayer.put(9, 69300);
        powerPerPlayer.put(10, 72000);
        powerPerPlayer.put(11, 80400);
        powerPerPlayer.put(12, 84300);
        powerPerPlayer.put(13, 93600);
        powerPerPlayer.put(14, 99600);
        powerPerPlayer.put(15, 105600);
        powerPerPlayer.put(16, 111600);
        powerPerPlayer.put(17, 117600);
        powerPerPlayer.put(18, 123600);
        powerPerPlayer.put(19, 129600);
        powerPerPlayer.put(20, 135600);

        //powerPerPlayer.put(0, 23600);
    }

    public int getPowerForPlayer(int number) {
        return powerPerPlayer.getOrDefault(number, powerPerPlayer.get(0));
    }
}
