package com.regisx001.minigit.core.commands;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

            String head = Files.readString(repo.mainBranch()).trim();
            if (!head.isEmpty()) {
                String commitText = new String(store.read(head), StandardCharsets.UTF_8);
                String treeHash = commitText.split("tree ")[1].split("\n")[0];
                String treeText = new String(store.read(treeHash), StandardCharsets.UTF_8);

                for (String line : treeText.split("\n")) {
                    if (line.startsWith("100644")) {
                        committedFiles.add(line.split(" ")[1]);
                    }
                }
            }

            Set<String> workingFiles = Files.list(Path.of("."))
                    .filter(p -> Files.isRegularFile(p))
                    .map(p -> p.getFileName().toString())
                    .filter(f -> !f.equals(".minigit"))
                    .collect(HashSet::new, HashSet::add, HashSet::addAll);

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

}
