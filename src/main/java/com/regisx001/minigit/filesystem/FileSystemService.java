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

    public void createFile(Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create file: " + path, e);
        }
    }

    public void writeFile(Path path, String content) {
        try {
            Files.writeString(path, content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file: " + path, e);
        }
    }

    public boolean exists(Path path) {
        return Files.exists(path);
    }
}
