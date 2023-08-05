package me.fly.factions.impl.commands.faction.invite;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.impl.commands.faction.invite.add.InviteAddCommand;
import me.fly.factions.impl.commands.faction.invite.faction.InviteFactionCommand;

public class InviteCommand extends CommandDivision {
    public InviteCommand() {
        addHelpEntry("/f invite add <user>", "Invite a user to the faction");
        addHelpEntry("/f invite faction <faction>", "Invite a faction to merge into you");

        addSubCommand("add", new InviteAddCommand());
        addSubCommand("faction", new InviteFactionCommand());
    }
}
