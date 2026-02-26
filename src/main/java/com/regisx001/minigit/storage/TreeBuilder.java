package com.regisx001.minigit.storage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.regisx001.minigit.domain.Tree;
import com.regisx001.minigit.domain.TreeEntry;

public class TreeBuilder {

    private final ObjectStore store;

    public TreeBuilder(ObjectStore store) {
        this.store = store;
    }

    private static class DirectoryNode {
        Map<String, DirectoryNode> directories = new HashMap<>();
        Map<String, String> files = new HashMap<>();
    }

    public String build(Map<String, String> indexEntries) {
        DirectoryNode root = new DirectoryNode();

        for (Map.Entry<String, String> entry : indexEntries.entrySet()) {
            insertPath(root, entry.getKey(), entry.getValue());
        }

        return buildTreeRecursive(root);
    }

    private String buildTreeRecursive(DirectoryNode node) {
        List<TreeEntry> entries = new ArrayList<>();

        for (Map.Entry<String, DirectoryNode> dir : node.directories.entrySet()) {
            String childHash = buildTreeRecursive(dir.getValue());
            entries.add(new TreeEntry("040000", dir.getKey(), childHash));
        }

        for (Map.Entry<String, String> file : node.files.entrySet()) {
            entries.add(new TreeEntry("100644", file.getKey(), file.getValue()));
        }

        entries.sort(Comparator.comparing(e -> e.serialize()));

        Tree tree = new Tree(entries);
        store.store(tree.hash(), tree.serialize());

        return tree.hash();
    }

    private void insertPath(DirectoryNode root, String path, String blobHash) {
        String[] parts = path.split("/");
        DirectoryNode current = root;

        for (int i = 0; i < parts.length - 1; i++) {
            current = current.directories
                    .computeIfAbsent(parts[i], k -> new DirectoryNode());
        }

        current.files.put(parts[parts.length - 1], blobHash);
    }
}
