package me.fly.factions.impl.commands.faction.province.set.format.fill;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.Region;
import me.fly.factions.api.model.User;
import me.fly.factions.impl.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;

public class ProvinceSetFormatFillCommand extends CommandDivision {
    public ProvinceSetFormatFillCommand() {
        addHelpEntry("/f province set format fill <province name> <fillRed> <fillGreen> <fillBlue> <fillOpacity>", "Set the province dynmap fill format");


        addSubCommand("*", this);
    }

    @SuppressWarnings({"Pain please tell me what the NPE supression is", "unused"})
    public boolean run(CommandSender sender, String region, String r, String g, String b, String o) {
        User user = USERS.get(Bukkit.getPlayer(sender.getName()).getUniqueId());
        Faction faction = user.getFaction();
        Region regionr = faction.getRegion(region);

        if (regionr == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: the province " + ChatColor.YELLOW + region + ChatColor.RED + " does not exist");

            return false;
        }

        int ri = constrain(0, 255, Integer.parseInt(r));
        int gi = constrain(0, 255, Integer.parseInt(g));
        int bi = constrain(0, 255, Integer.parseInt(b));

        double od = constrain(0.25, 0.75, Double.parseDouble(o));

        regionr.setFillColor(Color.fromRGB(ri, gi, bi));
        regionr.setFillOpacity(od);

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully set fill format to " + ChatColor.YELLOW + ri + "," + gi + "," + bi + "; " + od);

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.STRING,
                ArgumentType.INT,
                ArgumentType.INT,
                ArgumentType.INT,
                ArgumentType.DOUBLE
        };
    }

    @Override
    public Pair<CommandRequirement, Object>[] getUserRequirements() {
        return new Pair[] {
                new Pair<>(CommandRequirement.REQUIRE_PLAYER, null),
                new Pair<>(CommandRequirement.REQUIRE_MEMBER_FACTION, null),
                new Pair<>(CommandRequirement.REQUIRE_REGION_LEADER, 0)
        };
    }
}
