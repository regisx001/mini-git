package com.regisx001.minigit.core.commands;

import com.regisx001.minigit.core.Command;

public class InitCommand implements Command{

    @Override
    public void execute() {
        System.out.println("Intializing minigit repository ...");
    }
    
}
