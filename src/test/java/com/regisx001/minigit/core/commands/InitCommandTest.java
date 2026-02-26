package com.regisx001.minigit.core.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class InitCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void testExecute() throws IOException {
        // Change working directory to tempDir for the test
        String originalDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());

        try {
            InitCommand command = new InitCommand();
            command.execute();

            Path minigitDir = tempDir.resolve(".minigit");
            assertTrue(Files.exists(minigitDir));
            assertTrue(Files.isDirectory(minigitDir.resolve("objects")));
            assertTrue(Files.isDirectory(minigitDir.resolve("refs/heads")));
            assertTrue(Files.exists(minigitDir.resolve("index")));
            assertTrue(Files.exists(minigitDir.resolve("HEAD")));
            assertTrue(Files.exists(minigitDir.resolve("refs/heads/main")));

            assertEquals("refs/heads/main", Files.readString(minigitDir.resolve("HEAD")));

            // Test running it again throws exception
            assertThrows(RuntimeException.class, () -> command.execute());
        } finally {
            // Restore original working directory
            System.setProperty("user.dir", originalDir);
        }
    }
}
