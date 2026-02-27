package com.regisx001.minigit.core.commands;

import java.util.Map;

import com.regisx001.minigit.core.Command;
import com.regisx001.minigit.core.Repository;
import com.regisx001.minigit.core.RepositoryLoader;
import com.regisx001.minigit.domain.Commit;
import com.regisx001.minigit.filesystem.FileSystemService;
import com.regisx001.minigit.storage.IgnoreService;
import com.regisx001.minigit.storage.Index;
import com.regisx001.minigit.storage.ObjectStore;
import com.regisx001.minigit.storage.RefStore;
import com.regisx001.minigit.storage.TreeBuilder;

public class CommitCommand implements Command {

    private final String message;
    IgnoreService ignoreService = new IgnoreService();

    public CommitCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        Repository repo = new RepositoryLoader().load();
        FileSystemService fs = new FileSystemService();

        Index index = new Index(repo.indexFile(), fs);
        Map<String, String> rawEntries = index.readEntries();

        Map<String, String> entries = rawEntries.entrySet().stream()
                .filter(e -> !ignoreService.isIgnored(e.getKey()))
                .collect(java.util.stream.Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));

        if (entries.isEmpty()) {
            throw new RuntimeException("Nothing to commit");
        }

        ObjectStore store = new ObjectStore(repo.objectsDir(), fs);

        TreeBuilder builder = new TreeBuilder(store);
        String rootTreeHash = builder.build(entries);

        RefStore refs = new RefStore(repo, fs);
        String parent = refs.readCurrentCommit();

        if (parent != null) {
            String parentCommitText = new String(store.read(parent), java.nio.charset.StandardCharsets.UTF_8);

            String parentTreeHash = parentCommitText.split("tree ")[1].split("\n")[0];

            if (parentTreeHash.equals(rootTreeHash)) {
                throw new RuntimeException("Nothing to commit (working tree clean)");
            }
        }

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