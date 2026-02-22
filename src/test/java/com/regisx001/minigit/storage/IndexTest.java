package com.regisx001.minigit.storage;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.regisx001.minigit.filesystem.FileSystemService;

public class IndexTest {

    @TempDir
    Path tempDir;

    @Test
    public void testIndexOperations() {
        FileSystemService fs = new FileSystemService();
        Path indexFile = tempDir.resolve("index");
        fs.createFile(indexFile);

        Index index = new Index(indexFile, fs);

        // Test writeAll and readAll
        List<String> lines = Arrays.asList("file1.txt hash1", "file2.txt hash2");
        index.writeAll(lines);
        assertEquals(lines, index.readAll());

        // Test readEntries
        Map<String, String> entries = index.readEntries();
        assertEquals(2, entries.size());
        assertEquals("hash1", entries.get("file1.txt"));
        assertEquals("hash2", entries.get("file2.txt"));

        // Test writeEntries
        Map<String, String> newEntries = new HashMap<>();
        newEntries.put("file3.txt", "hash3");
        index.writeEntries(newEntries);

        Map<String, String> readNewEntries = index.readEntries();
        assertEquals(1, readNewEntries.size());
        assertEquals("hash3", readNewEntries.get("file3.txt"));
    }
}
