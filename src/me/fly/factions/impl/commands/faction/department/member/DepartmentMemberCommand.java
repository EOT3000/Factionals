package me.fly.factions.impl.commands.faction.department.member;

import me.fly.factions.api.commands.CommandDivision;
import me.fly.factions.impl.commands.faction.department.member.add.DepartmentMemberAddCommand;
import me.fly.factions.impl.commands.faction.department.member.remove.DepartmentMemberRemoveCommand;

public class DepartmentMemberCommand extends CommandDivision {
    public DepartmentMemberCommand() {
        addSubCommand("add", new DepartmentMemberAddCommand());
        addSubCommand("remove", new DepartmentMemberRemoveCommand());

        addHelpEntry("/f department member add <user> <department>", "Add a user to the department");
        addHelpEntry("/f department member remove <user> <department>", "Removes a user from the department");
    }
}
