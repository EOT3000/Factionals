package fly.factions.impl.commands.faction.department;

import fly.factions.api.commands.CommandDivision;
import fly.factions.impl.commands.faction.department.create.DepartmentCreateCommand;
import fly.factions.impl.commands.faction.department.member.DepartmentMemberCommand;
import fly.factions.impl.commands.faction.department.set.DepartmentSetCommand;

public class DepartmentCommand extends CommandDivision {
    public DepartmentCommand() {
        addHelpEntry("/f department create <name>", "Create a department with the given name");

        addHelpEntry("/f department set", "View department settings commands");

        addHelpEntry("/f department member", "View department member commands");


        addSubCommand("set", new DepartmentSetCommand());

        addSubCommand("create", new DepartmentCreateCommand());

        addSubCommand("member", new DepartmentMemberCommand());
    }
}
