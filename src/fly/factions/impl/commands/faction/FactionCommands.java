package fly.factions.impl.commands.faction;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.PlotCommand;
import fly.factions.impl.commands.faction.claim.ClaimCommand;
import fly.factions.impl.commands.faction.create.CreateCommand;
import fly.factions.impl.commands.faction.department.DepartmentCommand;
import fly.factions.impl.commands.faction.info.InfoCommand;
import fly.factions.impl.commands.faction.invite.InviteCommand;
import fly.factions.impl.commands.faction.join.FactionJoinCommand;
import fly.factions.impl.commands.faction.list.ListCommand;
import fly.factions.impl.commands.faction.map.MapCommand;
import fly.factions.impl.commands.faction.plot.PlotCommands;
import fly.factions.impl.commands.faction.region.RegionCommand;
import fly.factions.impl.commands.faction.town.TownCommand;
import fly.factions.impl.commands.faction.unclaim.UnclaimCommand;

public class FactionCommands extends CommandDivision {
    public FactionCommands() {
        super("f");

        addHelpEntry("/f", "Open this menu");

        addHelpEntry("/f create <name>", "Create a faction with the given name");

        addHelpEntry("/f invite", "View member invite commands");

        addHelpEntry("/f join <faction>", "Attempt to join the given faction");

        addHelpEntry("/f claim", "Claim one chunk at your location for your faction");

        addHelpEntry("/f unclaim", "Unclaim one chunk at your location for your faction");

        //addHelpEntry("/f claim fill", "Fills in a hollow-ly filled area with chunks");

        addHelpEntry("/f info <faction name>", "View information about a faction");

        addHelpEntry("/f map", "View a map of nearby chunks and their factions");

        addHelpEntry("/f list", "Get a list of all factions");

        //addHelpEntry("/f set", "View faction settings commands");

        addHelpEntry("/f region", "View region commands");

        addHelpEntry("/f town", "View town commands");

        addHelpEntry("/f department", "Not Yet Implemented (WIP)");

        addHelpEntry("/f plot", "View plot commands");


        addSubCommand("create", new CreateCommand());

        addSubCommand("invite", new InviteCommand());

        addSubCommand("join", new FactionJoinCommand());

        
        addSubCommand("claim", new ClaimCommand());
        addSubCommand("unclaim", new UnclaimCommand());


        addSubCommand("list", new ListCommand());
        addSubCommand("map", new MapCommand());
        addSubCommand("info", new InfoCommand());


        addSubCommand("region", new RegionCommand());

        addSubCommand("town", new TownCommand());

        addSubCommand("department", new DepartmentCommand());

        //Broken shit
        addSubCommand("plot", new PlotCommands());
    }
}
