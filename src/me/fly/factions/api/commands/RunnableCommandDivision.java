package me.fly.factions.api.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;


//TODO: finish

public class RunnableCommandDivision extends CommandDivision {
    private List<CommandRequirement> requirements = new ArrayList<>();
    private CommandDivision.ArgumentType[] arguments;

    protected RunnableCommandDivision() {

    }

    protected RunnableCommandDivision(ArgumentType... types) {
        this.arguments = types;
    }

    public final boolean run(CommandSender sender, String[] args) {
        return false;
    }

    public final boolean checkArguments(String[] strings) {
        return false;

        //if(ArgumentType.checkAll())
    }

    protected void addRequirement(CommandRequirement requirement) {
        requirements.add(requirement);
    }
}
