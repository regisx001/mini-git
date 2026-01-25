package com.regisx001.minigit;

import com.regisx001.minigit.cli.CommandParser;
import com.regisx001.minigit.core.Command;

public class App {
    public static void main(String[] args) {
        try {
            CommandParser parser = new CommandParser();
            Command command = parser.parse(args);
            command.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}