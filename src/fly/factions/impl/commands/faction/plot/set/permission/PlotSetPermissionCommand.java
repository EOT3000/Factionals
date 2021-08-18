package fly.factions.impl.commands.faction.plot.set.permission;

import fly.factions.api.commands.CommandDivision;
import fly.factions.api.commands.CommandRegister;
import fly.factions.api.commands.CommandRequirement;
import fly.factions.api.model.*;
import fly.factions.api.permissions.Permissibles;
import fly.factions.api.permissions.PlotPermission;
import fly.factions.impl.util.Pair;
import fly.factions.impl.util.Plots;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PlotSetPermissionCommand extends CommandDivision {
    private static final Pattern permType = Pattern.compile("[adfpr]");

    public PlotSetPermissionCommand() {
        addSubCommand("*", this);
    }

    public boolean run(CommandSender sender, String lotId, String permission, String permissible, String type) {
        Location location = ((Player) sender).getLocation();

        User user = USERS.get(((Player) sender).getUniqueId());

        Plot plot = PLOTS.get(Plots.getLocationId(location));

        if(plot == null || (plot.getAdministrator() instanceof Region)) {
            sender.sendMessage(ChatColor.RED + "Error: not inside of a region");

            return false;
        }

        Lot lot = ((Region) plot.getAdministrator()).getLots().get(Integer.parseInt(lotId));

        String permissibleType = "a";
        String permissible2 = permissible;


        if(permissible.charAt(1) == ':') {
            permissibleType = permissible.split(":")[0];
            permissible = permissible.split(":")[1];
        }

        if(!permType.matcher(permissibleType).matches()) {
            sender.sendMessage(ChatColor.RED + "ERROR: the class selector " + ChatColor.YELLOW + permissibleType + ":" + ChatColor.RED + " is invalid! Please use a a valid selector");
            return false;
        }

        List<Permissible> result = Permissibles.get(permissible);

        for(Permissible r : new ArrayList<>(result)) {
            if(permissibleType.equalsIgnoreCase("a")) {
                break;
            }

            if(permissibleType.equalsIgnoreCase("f") && !(r instanceof Faction)) {
                result.remove(r);
                continue;
            }

            if(permissibleType.equalsIgnoreCase("p") && !(r instanceof User)) {
                result.remove(r);
                continue;
            }

            if(permissibleType.equalsIgnoreCase("r") && !(r instanceof Region)) {
                result.remove(r);
                continue;
            }

            if(permissibleType.equalsIgnoreCase("d") && !(r instanceof ExecutiveDivision)) {
                result.remove(r);
            }
        }

        try {
            new CommandRegister.NumberPotential(1)
                    .less((x) -> {
                        sender.sendMessage(ChatColor.RED + "ERROR: no valid object is called " + ChatColor.YELLOW + permissible2);
                        throw new CommandRegister.ReturnNowException();
                    })
                    .equal((x) -> {
                        if (!lot.getOwner().userHasPlotPermissions(user, true, false)) {
                            sender.sendMessage(ChatColor.RED + "ERROR: you do not own this plot");
                            throw new CommandRegister.ReturnNowException();
                        }
                    })
                    .more((x) -> {
                        sender.sendMessage(ChatColor.RED + "ERROR: " + ChatColor.YELLOW + permissible2 + ChatColor.RED + " is ambiguous! Please specify the permissible type using '<object type>:<object name>' instead");
                        throw new CommandRegister.ReturnNowException();
                    }).run(result.size());
        } catch (CommandRegister.ReturnNowException e) {
            return false;
        }

        try {
            PlotPermission plotPermission = PlotPermission.valueOf(permission);

            lot.setPermission(result.get(0), plotPermission, p(type));

            sender.sendMessage(ChatColor.GREEN + "Successfully set " + ChatColor.YELLOW + plotPermission + ChatColor.RED + " of " + ChatColor.YELLOW + permissible + ChatColor.RED + " to " + ChatColor.YELLOW + p(type));

            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public ArgumentType[] getRequiredTypes() {
        return new ArgumentType[] {
                ArgumentType.INT,
                ArgumentType.FACTION_PERMISSION,
                ArgumentType.PERMISSIBLE,
                ArgumentType.CHOICE
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

    public boolean p(String s) {
        return Boolean.parseBoolean(s) || (s.equalsIgnoreCase("yes") || (!s.equalsIgnoreCase("false") && s.equalsIgnoreCase("no")));
    }
}
