package com.regisx001.minigit.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.regisx001.minigit.exceptions.ExistingRepositoryException;

public class FileSystemService {

    public void createDirectory(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException exception) {
            throw new ExistingRepositoryException("MiniGit repository already exists.");
        }
    }

    public boolean exists(Path path) {
        return Files.exists(path);
    }
}
