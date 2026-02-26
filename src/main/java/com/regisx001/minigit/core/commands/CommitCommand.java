package com.regisx001.minigit.core.commands;

import java.util.Map;

import com.regisx001.minigit.core.Command;
import com.regisx001.minigit.core.Repository;
import com.regisx001.minigit.core.RepositoryLoader;
import com.regisx001.minigit.domain.Commit;
import com.regisx001.minigit.filesystem.FileSystemService;
import com.regisx001.minigit.storage.Index;
import com.regisx001.minigit.storage.ObjectStore;
import com.regisx001.minigit.storage.RefStore;
import com.regisx001.minigit.storage.TreeBuilder;

public class CommitCommand implements Command {

    private final String message;

    public CommitCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        Repository repo = new RepositoryLoader().load();
        FileSystemService fs = new FileSystemService();

        Index index = new Index(repo.indexFile(), fs);
        Map<String, String> entries = index.readEntries();

        if (entries.isEmpty()) {
            throw new RuntimeException("Nothing to commit");
        }

        ObjectStore store = new ObjectStore(repo.objectsDir(), fs);

        TreeBuilder builder = new TreeBuilder(store);
        String rootTreeHash = builder.build(entries);

        RefStore refs = new RefStore(repo, fs);
        String parent = refs.readCurrentCommit();

        Commit commit = new Commit(
                rootTreeHash,
                parent,
                "You",
                System.currentTimeMillis() / 1000,
                message);

        store.store(commit.hash(), commit.serialize());
        refs.updateCurrentCommit(commit.hash());
        index.writeEntries(Map.of());

        System.out.println("Committed: " + commit.hash());
    }
}