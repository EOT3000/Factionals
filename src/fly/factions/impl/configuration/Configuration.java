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
            total+=entry.getValue();

            //System.out.println("powerPerPlayer.put(" + entry.getKey() + ", " + entry.getValue()*6 + ");");
            System.out.println(total/entry.getKey());
        }
    }

    //TODO: configuration

    private final Map<Integer, Integer> powerPerPlayer = new HashMap<>();

    public Configuration() {
        powerPerPlayer.put(1, 200);
        powerPerPlayer.put(2, 210);
        powerPerPlayer.put(3, 230);
        powerPerPlayer.put(4, 250);
        powerPerPlayer.put(5, 275);
        powerPerPlayer.put(6, 300);
        powerPerPlayer.put(7, 325);
        powerPerPlayer.put(8, 325);
        powerPerPlayer.put(9, 350);
        powerPerPlayer.put(10, 400);
        powerPerPlayer.put(11, 450);
        powerPerPlayer.put(12, 500);
        powerPerPlayer.put(13, 495);
        powerPerPlayer.put(14, 490);
        powerPerPlayer.put(15, 485);
        powerPerPlayer.put(16, 480);
        powerPerPlayer.put(17, 470);
        powerPerPlayer.put(18, 460);
        powerPerPlayer.put(19, 450);
        powerPerPlayer.put(20, 440);

        //powerPerPlayer.put(0, 23600);
    }

    public int getPowerForPlayer(int number) {
        return powerPerPlayer.getOrDefault(number, powerPerPlayer.get(0))*17;
    }
}
