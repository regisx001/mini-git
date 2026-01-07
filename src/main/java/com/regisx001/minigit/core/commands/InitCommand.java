package com.regisx001.minigit.core.commands;

import java.nio.file.Path;

import com.regisx001.minigit.core.Command;
import com.regisx001.minigit.filesystem.FileSystemService;

public class InitCommand implements Command {

    private final FileSystemService fs = new FileSystemService();

    @Override
    public void execute() {
        Path repo = Path.of(".minigit");

        if (fs.exists(repo)) {
            throw new RuntimeException("MiniGit repository already exists.");
        }

        fs.createDirectory(repo);
        fs.createDirectory(repo.resolve("objects"));
        fs.createDirectory(repo.resolve("refs/heads"));

        fs.createFile(repo.resolve("index"));
        fs.createFile(repo.resolve("HEAD"));
        fs.writeFile(repo.resolve("HEAD"), "refs/heads/main");

        fs.createFile(repo.resolve("refs/heads/main"));

        System.out.println("Repository does not exist. Ready to initialize.");

    }

}
