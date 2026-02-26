package com.regisx001.minigit.core.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class StatusCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void testExecute() throws IOException {
        String originalDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());

        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Initialize repository
            new InitCommand().execute();

            // Create a file
            Path testFile = tempDir.resolve("test.txt");
            Files.writeString(testFile, "Hello, Minigit!");

            // Execute StatusCommand
            StatusCommand statusCommand = new StatusCommand();
            statusCommand.execute();

            String output = outContent.toString();
            assertTrue(output.contains("Untracked files:"));
            assertTrue(output.contains("test.txt"));

            // Add the file
            new AddCommand("test.txt").execute();

            // Execute StatusCommand again
            outContent.reset();
            statusCommand.execute();

            output = outContent.toString();
            assertTrue(output.contains("Staged files:"));
            assertTrue(output.contains("test.txt"));

            // Commit the file
            new CommitCommand("Initial commit").execute();

            // Execute StatusCommand again
            outContent.reset();
            statusCommand.execute();

            output = outContent.toString();
            assertFalse(output.contains("test.txt"), "Output should not contain test.txt: " + output);

        } finally {
            System.setProperty("user.dir", originalDir);
            System.setOut(originalOut);
        }
    }

    @Test
    public void testExecuteIgnoredFile() throws IOException {
        String originalDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());

        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Initialize repository
            new InitCommand().execute();

            // Create .minigitignore
            Path ignoreFile = tempDir.resolve(".minigitignore");
            Files.writeString(ignoreFile, "*.log\n");

            // Create a file
            Path testFile = tempDir.resolve("test.log");
            Files.writeString(testFile, "Log data");

            // Execute StatusCommand
            StatusCommand statusCommand = new StatusCommand();
            statusCommand.execute();

            String output = outContent.toString();
            assertFalse(output.contains("test.log"));

        } finally {
            System.setProperty("user.dir", originalDir);
            System.setOut(originalOut);
        }
    }
}
