package me.fly.factions.impl.commands.faction.province.set.format.border;

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

public class ProvinceSetFormatBorderCommand extends CommandDivision {
    public ProvinceSetFormatBorderCommand() {
        addHelpEntry("/f province set format border <province name> <borderRed> <borderGreen> <borderBlue>", "Set the province dynmap border color");


        addSubCommand("*", this);
    }

    @SuppressWarnings({"Pain please tell me what the NPE supression is", "unused"})
    public boolean run(CommandSender sender, String region, String r, String g, String b) {
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

        regionr.setBorderColor(Color.fromRGB(ri, gi, bi));

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully set border format to " + ChatColor.YELLOW + ri + "," + gi + "," + bi);

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.STRING,
                ArgumentType.INT,
                ArgumentType.INT,
                ArgumentType.INT
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

