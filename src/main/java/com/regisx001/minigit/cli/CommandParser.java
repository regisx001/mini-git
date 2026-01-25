package com.regisx001.minigit.cli;

import com.regisx001.minigit.core.Command;
import com.regisx001.minigit.core.commands.AddCommand;
import com.regisx001.minigit.core.commands.CommitCommand;
import com.regisx001.minigit.core.commands.InitCommand;

public class CommandParser {

    public Command parse(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("No command provided.");
        }

        String command = args[0];

        if (command.equals("init")) {
            return new InitCommand();
        }

        if (command.equals("add")) {
            if (args.length < 2) {
                throw new RuntimeException("add requires a file path");
            }
            return new AddCommand(args[1]);
        }

        if (command.equals("commit")) {
            if (args.length < 3 || !args[1].equals("-m")) {
                throw new RuntimeException("Usage: commit -m \"message\"");
            }
            return new CommitCommand(args[2]);
        }

        throw new RuntimeException("Unknown command: " + command);

    }
}