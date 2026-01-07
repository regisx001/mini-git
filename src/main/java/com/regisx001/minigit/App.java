package com.regisx001.minigit;

import com.regisx001.minigit.cli.CommandParser;
import com.regisx001.minigit.core.Command;

public class App {
    public static void main(String[] args) {
        CommandParser parser = new CommandParser();
        Command command = parser.parse(args);
        command.execute();

    }
}
