package com.regisx001.minigit.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.regisx001.minigit.core.Repository;
import com.regisx001.minigit.filesystem.FileSystemService;

public class RefStore {

    private final Repository repo;
    private final FileSystemService fs;

    public RefStore(Repository repo, FileSystemService fs) {
        this.repo = repo;
        this.fs = fs;
    }

    public String readHEAD() {
        return fs.readLines(repo.headFile()).get(0).trim();
    }

    public String readCurrentBranch() {
        String headRef = readHEAD(); // refs/heads/main
        return Path.of(headRef).getFileName().toString();
    }

    public String readCurrentCommit() {
        try {
            String headRef = readHEAD();
            Path branchPath = repo.root().resolve(headRef);
            String hash = Files.readString(branchPath).trim();
            return hash.isEmpty() ? null : hash;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read current commit", e);
        }
    }

    public void updateCurrentCommit(String hash) {
        String headRef = readHEAD();
        Path branchPath = repo.root().resolve(headRef);
        fs.writeFile(branchPath, hash);
    }
}