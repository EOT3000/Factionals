package fly.factions.impl.commands.faction.map;

import fly.factions.Factionals;
import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.Faction;
import fly.factions.api.model.Plot;
import fly.factions.api.model.User;
import fly.factions.impl.util.Pair;
import fly.factions.impl.util.Plots;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class MapCommand extends CommandDivision {
    public MapCommand() {
        addSubCommand("", this);
    }

    public boolean run(CommandSender sender) {
        User user = USERS.get(((Player) sender).getUniqueId());

        Faction userFaction = user.getFaction();

        List<Character> characters = new ArrayList<>(Arrays.asList('#', '&', '%', '$', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L'));
        Map<Faction, Character> factionCharacters = new HashMap<>();

        int height = 5;
        int width = 10;

        int xb = ((Player) sender).getLocation().getChunk().getX();
        int zb = ((Player) sender).getLocation().getChunk().getZ();
        World w = ((Player) sender).getLocation().getWorld();

        List<String> ret = new ArrayList<>();

        factionCharacters.put(null, '-');

        for(int z = -height; z <= height; z++) {
            String line = "";
            for(int x = -width; x <= width; x++) {
                int plotId = Plots.getLocationId((xb+x), (zb+z), w);

                Plot plot = Factionals.getFactionals().getRegistry(Plot.class, Integer.class).get(plotId);
                Faction faction = plot != null ? plot.getFaction() : null;
                String chunkAddition;

                if(faction == null || faction.isDeleted()) {
                    faction = null;
                }

                if(x == 0 && z == 0) {
                    chunkAddition = ChatColor.BLACK + "";
                } else if(faction == null) {
                    chunkAddition = ChatColor.GRAY + "";
                } else if(faction.equals(userFaction)) {
                    chunkAddition = ChatColor.GREEN + "";
                } else {
                    chunkAddition = ChatColor.DARK_GRAY + "";
                }

                if(faction != null && !factionCharacters.containsKey(faction)) {
                    factionCharacters.put(faction, characters.get(0));
                    characters.remove(0);
                }

                chunkAddition+=factionCharacters.get(faction);

                line+=chunkAddition;
            }
            ret.add(line);
        }

        for(String string : ret) {
            sender.sendMessage(string);
        }

        return true;
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null)
        };
    }
}
