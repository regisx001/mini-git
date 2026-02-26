package com.regisx001.minigit.core.commands;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.regisx001.minigit.core.Command;
import com.regisx001.minigit.core.Repository;
import com.regisx001.minigit.core.RepositoryLoader;
import com.regisx001.minigit.filesystem.FileSystemService;
import com.regisx001.minigit.storage.Index;
import com.regisx001.minigit.storage.ObjectStore;
import com.regisx001.minigit.storage.RefStore;

public class StatusCommand implements Command {

    @Override
    public void execute() {
        try {
            Repository repo = new RepositoryLoader().load();
            FileSystemService fs = new FileSystemService();
            ObjectStore store = new ObjectStore(repo.objectsDir(), fs);
            Index index = new Index(repo.indexFile(), fs);

            Map<String, String> indexEntries = index.readEntries();
            Set<String> committedFiles = new HashSet<>();

            RefStore refs = new RefStore(repo, fs);
            String head = refs.readCurrentCommit();

            if (head != null) {
                String commitText = new String(store.read(head), StandardCharsets.UTF_8);
                String treeHash = commitText.split("tree ")[1].split("\n")[0];
                collectFiles(treeHash, "", committedFiles, store);
            }

            Set<String> workingFiles = new HashSet<>();
            collectWorkingFiles(Path.of("."), "", workingFiles);

            System.out.println("Staged files:");
            indexEntries.keySet().forEach(f -> {
                if (!committedFiles.contains(f)) {
                    System.out.println("  " + f);
                }
            });

            System.out.println("\nUntracked files:");
            workingFiles.forEach(f -> {
                if (!indexEntries.containsKey(f) && !committedFiles.contains(f)) {
                    System.out.println("  " + f);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException("Failed to check status", e);
        }
    }

    private void collectFiles(String treeHash, String prefix,
            Set<String> files,
            ObjectStore store) {

        String treeText = new String(store.read(treeHash), StandardCharsets.UTF_8);

        for (String line : treeText.split("\n")) {
            if (line.isBlank())
                continue;

            String[] parts = line.split(" ");
            String mode = parts[0];
            String name = parts[1];
            String hash = parts[2];

            if (mode.equals("100644")) {
                files.add(prefix + name);
            } else if (mode.equals("040000")) {
                collectFiles(hash, prefix + name + "/", files, store);
            }
        }
    }

    private void collectWorkingFiles(Path directory,
            String prefix,
            Set<String> files) throws IOException {

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path path : stream) {

                if (path.getFileName().toString().equals(".minigit")) {
                    continue;
                }

                if (Files.isDirectory(path)) {
                    collectWorkingFiles(path,
                            prefix + path.getFileName().toString() + "/",
                            files);
                } else if (Files.isRegularFile(path)) {
                    files.add(prefix + path.getFileName().toString());
                }
            }
        }
    }
}