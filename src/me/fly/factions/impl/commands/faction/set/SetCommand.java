package me.fly.factions.impl.commands.faction.set;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.impl.commands.faction.set.description.SetDescriptionCommand;
import me.fly.factions.impl.commands.faction.set.format.SetFormatCommand;
import me.fly.factions.impl.commands.faction.set.home.SetHomeCommand;
import me.fly.factions.impl.commands.faction.set.open.SetOpenCommand;

public class SetCommand extends CommandDivision {
    public SetCommand() {
        addHelpEntry("/f set format", "See dynmap format commands");
        addHelpEntry("/f set description <desc>", "Set your faction's description");
        addHelpEntry("/f set open <true | false>", "Set the faction to open if true, or closed if false");
        addHelpEntry("/f set home", "Set the faction home to your location");

        addSubCommand("format", new SetFormatCommand());
        addSubCommand("description", new SetDescriptionCommand());
        addSubCommand("open", new SetOpenCommand());
        addSubCommand("home", new SetHomeCommand());
    }
}
