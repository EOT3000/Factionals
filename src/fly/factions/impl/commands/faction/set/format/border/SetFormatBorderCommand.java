package fly.factions.impl.commands.faction.set.format.border;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.Faction;
import fly.factions.api.model.Region;
import fly.factions.api.model.User;
import fly.factions.api.permissions.FactionPermission;
import fly.factions.impl.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;

public class SetFormatBorderCommand extends CommandDivision {
    public SetFormatBorderCommand() {
        addHelpEntry("/f set format border <borderRed> <borderGreen> <borderBlue>", "Set the faction dynmap border color");


        addSubCommand("*", this);
    }

    @SuppressWarnings({"Pain please tell me what the NPE supression is", "unused"})
    public boolean run(CommandSender sender, String r, String g, String b) {
        User user = USERS.get(Bukkit.getPlayer(sender.getName()).getUniqueId());
        Faction faction = user.getFaction();

        int ri = constrain(0, 255, Integer.parseInt(r));
        int gi = constrain(0, 255, Integer.parseInt(g));
        int bi = constrain(0, 255, Integer.parseInt(b));

        faction.setBorderColor(Color.fromRGB(ri, gi, bi));

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully set border format to " + ChatColor.YELLOW + ri + "," + gi + "," + bi);

        return true;
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
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
                new Pair<>(CommandRequirement.REQUIRE_USER_PERMISSION, FactionPermission.OWNER)
        };
    }
}

