package com.regisx001.minigit.core.commands;

import java.nio.file.Path;

import com.regisx001.minigit.core.Command;
import com.regisx001.minigit.core.Repository;
import com.regisx001.minigit.filesystem.FileSystemService;

public class InitCommand implements Command {

    private final FileSystemService fs = new FileSystemService();

    @Override
    public void execute() {
        Repository repo = new Repository(Path.of(".minigit"));

        if (fs.exists(repo.root())) {
            throw new RuntimeException("MiniGit repository already exists.");
        }

        fs.createDirectory(repo.root());
        fs.createDirectory(repo.root().resolve("objects"));
        fs.createDirectory(repo.root().resolve("refs/heads"));

        fs.createFile(repo.root().resolve("index"));
        fs.createFile(repo.root().resolve("HEAD"));
        fs.writeFile(repo.root().resolve("HEAD"), "refs/heads/main");

        fs.createFile(repo.root().resolve("refs/heads/main"));

        System.out.println("Repository does not exist. Ready to initialize.");

    }

}
