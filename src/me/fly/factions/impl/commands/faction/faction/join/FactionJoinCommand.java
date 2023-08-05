package me.fly.factions.impl.commands.faction.faction.join;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.Plot;
import me.fly.factions.api.model.Region;
import me.fly.factions.api.model.User;
import me.fly.factions.impl.model.RegionImpl;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class FactionJoinCommand extends CommandDivision {
    private Random random = new Random();

    public FactionJoinCommand() {
        addHelpEntry("/f faction join <faction>", "Merge your faction into another one");

        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String faction) {
        Faction join = FACTIONS.get(faction);

        Faction want = USERS.get(((Player) sender).getUniqueId()).getFaction();

        if(want.hasInviteFrom(join)) {
            Region region = new RegionImpl("merge_" + random.nextInt(), want.getLeader(), join);

            join.addRegion(region);

            for(Plot plot : want.getPlots()) {
                plot.setFaction(join);

                plot.setAdministrator(region);
            }

            for(User user : new ArrayList<>(want.getMembers())) {
                user.setFaction(join);
            }

            sender.sendMessage("done");

            return true;
        }

        return false;
    }
}
