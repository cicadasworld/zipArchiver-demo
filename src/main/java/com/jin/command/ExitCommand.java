package com.jin.command;

import com.jin.ConsoleHelper;

public class ExitCommand implements Command {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("see you around!");
    }
}
