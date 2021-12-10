package fly.factions.impl.commands.faction.plot.map;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.model.Faction;
import fly.factions.api.model.Lot;
import fly.factions.api.model.Plot;
import fly.factions.api.model.Region;
import fly.factions.impl.util.Pair;
import fly.factions.impl.util.Plots;
import net.kyori.adventure.text.*;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.function.Consumer;

public class PlotMapCommand extends CommandDivision {
    public PlotMapCommand() {
        addSubCommand("*", this);
        addSubCommand("", this);
    }

    public boolean run(CommandSender sender) {
        Player player = (Player) sender;

        Plot plot = PLOTS.get(Plots.getLocationId(player.getLocation()));

        if(plot == null) {
            sender.sendMessage("ERROR: not in a claimed chunk");

            return false;
        }

        Chunk chunk = player.getLocation().getChunk();

        String[][] array = new String[16][];

        for(int i = 0; i < 16; i++) {
            array[i] = new String[16];

            for(int i2 = 0; i2 < 16; i2++) {
                array[i][i2] = "{'text':'-', 'color':'gray'}";

                int x = player.getLocation().getBlockX() - chunk.getX()*16;
                int z = player.getLocation().getBlockZ() - chunk.getZ()*16;

                if(x == i && z == i2) {
                    array[i][i2] = "{'text':'-', 'color':'gold'}";
                }
            }
        }

        Map<Integer, Character> map = new HashMap<>();

        List<Character> characters = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                'V', 'W', 'X', 'Y', 'Z', '#', '$', '%', '&', '=',
                '+', '?', '/', '\\'));

        for(Pair<Integer, Integer> location : findLots(plot, chunk)) {
            int x = location.getKey();
            int z = location.getValue();

            int lotId = ((Region) plot.getAdministrator()).getLot(chunk.getBlock(x, 0, z).getLocation()).getId();

            if(map.get(lotId) == null) {
                map.put(lotId, characters.remove(0));
            }

            array[x][z] = "{'text':'" + map.get(lotId) + "', 'hoverEvent':{'action':'show_text', 'value':'&4'}, 'color':'green'}".replaceFirst("&4", lotId + "");

            if(location.getKey() == player.getLocation().getBlockX() && location.getValue() == player.getLocation().getBlockZ()) {
                array[x][z] = "{'text':'" + map.get(lotId) + "', 'hoverEvent':{'action':'show_text', 'value':'&4'}, 'color':'gold'}".replaceFirst("&4", lotId + "");
            }
        }

        for(int i = 0; i < 16; i++) {
            String string = "[";

            for(int i2 = 0; i2 < 16; i2++) {
                string+=", ";
                string+=array[i2][i];
            }

            string = ((string+"]").replaceFirst(", ", ""));

            player.sendMessage(GsonComponentSerializer.gson().deserialize(string));
        }

        return true;
    }

    private List<Pair<Integer, Integer>> findLots(Plot plot, Chunk chunk) {
        if(plot.getAdministrator() instanceof Faction) {
            return new ArrayList<>();
        }

        Region region = (Region) plot.getAdministrator();

        List<Pair<Integer, Integer>> ret = new ArrayList<>();

        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                Lot lot = region.getLot(chunk.getWorld(), chunk.getX()*16+x, chunk.getZ()*16+z);

                if(lot != null) {
                   ret.add(new Pair<>(x, z));
                }
            }
        }

        return ret;
    }
}
