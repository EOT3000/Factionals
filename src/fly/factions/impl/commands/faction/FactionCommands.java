package fly.factions.impl.commands.faction;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.admin.AdminCommand;
import fly.factions.impl.commands.faction.autoClaim.AutoClaimCommand;
import fly.factions.impl.commands.faction.chat.ChatCommand;
import fly.factions.impl.commands.faction.claim.ClaimCommand;
import fly.factions.impl.commands.faction.create.CreateCommand;
import fly.factions.impl.commands.faction.department.DepartmentCommand;
import fly.factions.impl.commands.faction.disband.DisbandCommand;
import fly.factions.impl.commands.faction.faction.FactionCommand;
import fly.factions.impl.commands.faction.info.InfoCommand;
import fly.factions.impl.commands.faction.invite.InviteCommand;
import fly.factions.impl.commands.faction.join.JoinCommand;
import fly.factions.impl.commands.faction.kick.KickCommand;
import fly.factions.impl.commands.faction.leave.LeaveCommand;
import fly.factions.impl.commands.faction.list.ListCommand;
import fly.factions.impl.commands.faction.listOpen.ListOpenCommand;
import fly.factions.impl.commands.faction.map.MapCommand;
import fly.factions.impl.commands.faction.organization.OrganizationCommand;
import fly.factions.impl.commands.faction.overclaim.OverclaimCommand;
import fly.factions.impl.commands.faction.player.PlayerCommand;
import fly.factions.impl.commands.faction.plot.PlotCommands;
import fly.factions.impl.commands.faction.province.ProvinceCommand;
import fly.factions.impl.commands.faction.rename.RenameCommand;
import fly.factions.impl.commands.faction.set.SetCommand;
import fly.factions.impl.commands.faction.town.TownCommand;
import fly.factions.impl.commands.faction.unclaim.UnclaimCommand;

public class FactionCommands extends CommandDivision {
    public FactionCommands() {
        super("f", "faction", "factions");

        addHelpEntry("/f", "Open this menu");

        addHelpEntry("/f create <name>", "Create a faction with the given name");

        addHelpEntry("/f invite", "View member invite commands");

        addHelpEntry("/f kick <user>", "Kicks a user from the faction");

        addHelpEntry("/f join <faction>", "Attempt to join the given faction");

        addHelpEntry("/f claim <fill | one | auto>", "Claims either one chunk or fills in a hollow spot, or enabled autoclaim");

        addHelpEntry("/f autoClaim", "Toggle whether you are auto claiming");

        //addHelpEntry("/f claim fill", "Fills in a hollow-ly filled area with chunks");

        addHelpEntry("/f info <faction name>", "View information about a faction");

        addHelpEntry("/f player <player name>", "View information about a player");

        addHelpEntry("/f map", "View a map of nearby chunks and their factions");

        addHelpEntry("/f list", "Get a list of all factions");

        addHelpEntry("/f listOpen", "Get a list of all factions");

        addHelpEntry("/f set", "View faction settings commands");

        addHelpEntry("/f province", "View province commands");

        addHelpEntry("/f town", "View town commands");

        addHelpEntry("/f department", "Not Yet Implemented (WIP)");

        addHelpEntry("/f plot", "View plot commands");

        addHelpEntry("/f organization", "View organization commands");

        addHelpEntry("/f faction", "View faction merger commands");

        addHelpEntry("/f rename <name>", "Rename your faction");

        addHelpEntry("/f leave", "Leave your faction");

        addHelpEntry("/f disband", "Disband your faction");

        addHelpEntry("/f unclaim <all | one | fill | auto>", "Unclaims some amount of chunks, or enables auto unclaiming. ");

        addHelpEntry("/f chat", "Toggles faction chat");

        addHelpEntry("/f overclaim", "Claims the chunk you are on for your faction");



        addSubCommand("create", new CreateCommand());

        addSubCommand("admin", new AdminCommand());

        addSubCommand("invite", new InviteCommand());
        addSubCommand("kick", new KickCommand());

        addSubCommand("join", new JoinCommand());

        
        addSubCommand("claim", new ClaimCommand());
        addSubCommand("unclaim", new UnclaimCommand());
        addSubCommand("autoClaim", new AutoClaimCommand());


        addSubCommand("list", new ListCommand());
        addSubCommand("listOpen", new ListOpenCommand());
        addSubCommand("map", new MapCommand());
        addSubCommand("info", new InfoCommand());


        addSubCommand("province", new ProvinceCommand());

        addSubCommand("town", new TownCommand());

        addSubCommand("department", new DepartmentCommand());

        addSubCommand("set", new SetCommand());

        //Broken shit
        addSubCommand("plot", new PlotCommands());

        addSubCommand("faction", new FactionCommand());

        addSubCommand("rename", new RenameCommand());

        addSubCommand("leave", new LeaveCommand());

        addSubCommand("disband", new DisbandCommand());

        addSubCommand("chat", new ChatCommand());

        addSubCommand("player", new PlayerCommand());

        addSubCommand("overclaim", new OverclaimCommand());
        addSubCommand("organization", new OrganizationCommand());


    }
}
