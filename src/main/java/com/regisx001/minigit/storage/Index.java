package com.regisx001.minigit.storage;

import java.nio.file.Path;
import java.util.List;

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

}
