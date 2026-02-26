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
import com.regisx001.minigit.storage.RefStore;

import static org.junit.jupiter.api.Assertions.*;

public class CommitCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void testExecute() throws IOException {
        String originalDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());

        try {
            // Initialize repository
            new InitCommand().execute();

            // Create and add a file
            Path testFile = tempDir.resolve("test.txt");
            Files.writeString(testFile, "Hello, Minigit!");
            new AddCommand("test.txt").execute();

            // Execute CommitCommand
            CommitCommand commitCommand = new CommitCommand("Initial commit");
            commitCommand.execute();

            // Verify commit
            Repository repo = new RepositoryLoader().load();
            FileSystemService fs = new FileSystemService();
            RefStore refs = new RefStore(repo, fs);
            String currentCommit = refs.readCurrentCommit();

            assertNotNull(currentCommit);
            Path commitFile = repo.objectsDir().resolve(currentCommit.substring(0, 2)).resolve(currentCommit.substring(2));
            assertTrue(Files.exists(commitFile));

            // Verify index is cleared
            Index index = new Index(repo.indexFile(), fs);
            Map<String, String> entries = index.readEntries();
            assertTrue(entries.isEmpty());

        } finally {
            System.setProperty("user.dir", originalDir);
        }
    }

    @Test
    public void testExecuteNothingToCommit() throws IOException {
        String originalDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());

        try {
            // Initialize repository
            new InitCommand().execute();

            // Execute CommitCommand without adding files
            CommitCommand commitCommand = new CommitCommand("Empty commit");

            assertThrows(RuntimeException.class, () -> commitCommand.execute(), "Nothing to commit");

        } finally {
            System.setProperty("user.dir", originalDir);
        }
    }
}
