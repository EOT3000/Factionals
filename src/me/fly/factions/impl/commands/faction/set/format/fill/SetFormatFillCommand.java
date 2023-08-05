package me.fly.factions.impl.commands.faction.set.format.fill;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.api.commands.CommandRequirement;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.model.User;
import me.fly.factions.api.permissions.FactionPermission;
import me.fly.factions.impl.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;

public class SetFormatFillCommand extends CommandDivision {
    public SetFormatFillCommand() {
        addHelpEntry("/f set format fill <fillRed> <fillGreen> <fillBlue> <fillOpacity>", "Set the faction dynmap fill format");


        addSubCommand("*", this);
    }

    @SuppressWarnings({"Pain please tell me what the NPE supression is", "unused"})
    public boolean run(CommandSender sender, String r, String g, String b, String o) {
        User user = USERS.get(Bukkit.getPlayer(sender.getName()).getUniqueId());
        Faction faction = user.getFaction();

        int ri = constrain(0, 255, Integer.parseInt(r));
        int gi = constrain(0, 255, Integer.parseInt(g));
        int bi = constrain(0, 255, Integer.parseInt(b));

        double od = constrain(0.25, 0.75, Double.parseDouble(o));

        faction.setFillColor(Color.fromRGB(ri, gi, bi));
        faction.setFillOpacity(od);

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully set fill format to " + ChatColor.YELLOW + ri + "," + gi + "," + bi + "; " + od);

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
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
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.OWNER)
        };
    }
}
