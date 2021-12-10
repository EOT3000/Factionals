package fly.factions.impl.commands.faction.faction.join;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.model.Faction;
import fly.factions.api.model.Plot;
import fly.factions.api.model.Region;
import fly.factions.api.model.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class FactionJoinCommand extends CommandDivision {
    private Random random = new Random();

    public FactionJoinCommand() {
        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String faction) {
        Faction join = FACTIONS.get(faction);

        Faction want = USERS.get(((Player) sender).getUniqueId()).getFaction();

        if(want.hasInviteFrom(join)) {
            //for(Region region : want.getRegions())
            //want.joinRegion(join, region);

            for(Plot plot : want.getPlots()) {
                plot.setFaction(join);
            }

            for(User user : new ArrayList<>(want.getMembers())) {
                user.setFaction(join);
            }

            return true;
        }

        return false;
    }
}
