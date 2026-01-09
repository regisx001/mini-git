package com.regisx001.minigit.core.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.regisx001.minigit.core.Command;
import com.regisx001.minigit.core.Repository;
import com.regisx001.minigit.core.RepositoryLoader;
import com.regisx001.minigit.domain.Blob;
import com.regisx001.minigit.filesystem.FileSystemService;
import com.regisx001.minigit.storage.Index;
import com.regisx001.minigit.storage.ObjectStore;

public class AddCommand implements Command {

    private final String filePath;

    public AddCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void execute() {
        try {
            Repository repo = new RepositoryLoader().load();
            FileSystemService fs = new FileSystemService();

            byte[] content = Files.readAllBytes(Path.of(filePath));
            Blob blob = new Blob(content);

            ObjectStore store = new ObjectStore(repo.objectsDir(), fs);
            store.store(blob.hash(), blob.serialize());

            Index index = new Index(repo.indexFile(), fs);
            index.writeAll(List.of(filePath + " " + blob.hash()));

            System.out.println("Added " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to add file", e);
        }
    }
}
