package ru.otus.atmdepartment.director.command;

import java.util.ArrayList;
import java.util.List;

public class DepartmentCommand {

    private final List<AtmCommand> commands = new ArrayList<>();

    public void addCommand(AtmCommand command) {
        commands.add(command);
    }

    public void executeCommands() {
        commands.forEach(cmd -> cmd.execute());
    }
}
