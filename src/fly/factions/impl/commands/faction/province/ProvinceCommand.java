package fly.factions.impl.commands.faction.province;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.province.create.ProvinceCreateCommand;
import fly.factions.impl.commands.faction.province.delete.ProvinceDeleteCommand;
import fly.factions.impl.commands.faction.province.info.ProvinceInfoCommand;
import fly.factions.impl.commands.faction.province.lot.ProvinceLotCommand;
import fly.factions.impl.commands.faction.province.member.ProvinceMemberCommand;
import fly.factions.impl.commands.faction.province.set.ProvinceSetCommand;

public class ProvinceCommand extends CommandDivision {
    public ProvinceCommand() {
        addHelpEntry("/f province create <name>", "Create a province with the given name");

        addHelpEntry("/f province delete <name>", "Deletes given province");

        addHelpEntry("/f province set", "View province settings commands");

        addHelpEntry("/f province member", "View province member commands");

        addHelpEntry("/f province lot <province name>", "Add a lot");

        addHelpEntry("/f province info <faction name> <province name>", "View information about a province");


        addSubCommand("set", new ProvinceSetCommand());

        addSubCommand("create", new ProvinceCreateCommand());

        addSubCommand("delete", new ProvinceDeleteCommand());

        addSubCommand("lot", new ProvinceLotCommand());

        addSubCommand("info", new ProvinceInfoCommand());

        addSubCommand("member", new ProvinceMemberCommand());
    }
}
