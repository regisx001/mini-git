package com.regisx001.minigit.core.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.util.Map;

import com.regisx001.minigit.core.Repository;
import com.regisx001.minigit.core.RepositoryLoader;
import com.regisx001.minigit.filesystem.FileSystemService;
import com.regisx001.minigit.storage.Index;

import static org.junit.jupiter.api.Assertions.*;

public class AddCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void testExecute() throws IOException {
        String originalDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());

        try {
            // Initialize repository
            new InitCommand().execute();

            // Create a file to add
            Path testFile = tempDir.resolve("test.txt");
            Files.writeString(testFile, "Hello, Minigit!");

            // Execute AddCommand
            AddCommand addCommand = new AddCommand("test.txt");
            addCommand.execute();

            // Verify file is in index
            Repository repo = new RepositoryLoader().load();
            FileSystemService fs = new FileSystemService();
            Index index = new Index(repo.indexFile(), fs);
            Map<String, String> entries = index.readEntries();

            assertTrue(entries.containsKey("test.txt"));
            assertNotNull(entries.get("test.txt"));

            // Verify object is in object store
            String hash = entries.get("test.txt");
            Path objectFile = repo.objectsDir().resolve(hash.substring(0, 2)).resolve(hash.substring(2));
            assertTrue(Files.exists(objectFile));

        } finally {
            System.setProperty("user.dir", originalDir);
        }
    }

    @Test
    public void testExecuteIgnoredFile() throws IOException {
        String originalDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());

        try {
            // Initialize repository
            new InitCommand().execute();

            // Create .minigitignore
            Path ignoreFile = tempDir.resolve(".minigitignore");
            Files.writeString(ignoreFile, "*.log\n");

            // Create a file to add
            Path testFile = tempDir.resolve("test.log");
            Files.writeString(testFile, "Log data");

            // Execute AddCommand
            AddCommand addCommand = new AddCommand("test.log");
            addCommand.execute();

            // Verify file is NOT in index
            Repository repo = new RepositoryLoader().load();
            FileSystemService fs = new FileSystemService();
            Index index = new Index(repo.indexFile(), fs);
            Map<String, String> entries = index.readEntries();

            assertFalse(entries.containsKey("test.log"));

        } finally {
            System.setProperty("user.dir", originalDir);
        }
    }
}
