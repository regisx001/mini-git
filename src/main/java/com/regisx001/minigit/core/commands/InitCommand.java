package com.regisx001.minigit.core.commands;

import java.nio.file.Path;

import com.regisx001.minigit.core.Command;
import com.regisx001.minigit.filesystem.FileSystemService;

public class InitCommand implements Command {

    private final FileSystemService fs = new FileSystemService();

    @Override
    public void execute() {
        Path repoDir = Path.of(".minigit");

        if (fs.exists(repoDir)) {
            throw new RuntimeException("MiniGit repository already exists.");
        }

        System.out.println("Repository does not exist. Ready to initialize.");

    }

}
