package com.regisx001.minigit.storage;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.regisx001.minigit.filesystem.FileSystemService;

public class Index {

    private final Path indexFile;
    private final FileSystemService fs;

    public Index(Path indexFile, FileSystemService fs) {
        this.indexFile = indexFile;
        this.fs = fs;
    }

    public List<String> readAll() {
        return fs.readLines(indexFile);
    }

    public void writeAll(List<String> lines) {
        fs.writeLines(indexFile, lines);
    }

    public Map<String, String> readEntries() {
        Map<String, String> entries = new HashMap<>();
        for (String line : fs.readLines(indexFile)) {
            if (line.isBlank())
                continue;
            String[] parts = line.split(" ");
            entries.put(parts[0], parts[1]);
        }
        return entries;
    }

    public void writeEntries(Map<String, String> entries) {
        List<String> lines = entries.entrySet().stream()
                .map(e -> e.getKey() + " " + e.getValue())
                .toList();
        fs.writeLines(indexFile, lines);
    }
}
