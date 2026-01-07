package com.regisx001.minigit.core;

import java.nio.file.Path;

public class Repository {

    private final Path root;

    public Repository(Path root) {
        this.root = root;
    }

    public Path root() {
        return root;
    }

    public Path objectsDir() {
        return root.resolve("objects");
    }

    public Path refsHeadsDir() {
        return root.resolve("refs/heads");
    }

    public Path headFile() {
        return root.resolve("HEAD");
    }

    public Path indexFile() {
        return root.resolve("index");
    }

    public Path mainBranch() {
        return refsHeadsDir().resolve("main");
    }
}
