package com.regisx001.minigit.core;

import java.nio.file.Files;
import java.nio.file.Path;

public class RepositoryLoader {

    public Repository load() {
        Path root = Path.of(".minigit");

        if (!Files.exists(root)) {
            throw new RuntimeException("Not a MiniGit repository.");
        }

        return new Repository(root);
    }
}
