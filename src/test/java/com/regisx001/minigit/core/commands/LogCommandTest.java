package com.regisx001.minigit.core.commands;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LogCommandTest {

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

            // Create, add, and commit a file
            Path testFile = tempDir.resolve("test.txt");
            Files.writeString(testFile, "Hello, Minigit!");
            new AddCommand("test.txt").execute();
            new CommitCommand("Initial commit").execute();

            // Execute LogCommand
            LogCommand logCommand = new LogCommand();
            logCommand.execute();

            String output = outContent.toString();
            assertTrue(output.contains("commit "));
            assertTrue(output.contains("Initial commit"));

        } finally {
            System.setProperty("user.dir", originalDir);
            System.setOut(originalOut);
        }
    }

    @Test
    public void testExecuteNoCommits() throws IOException {
        String originalDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());

        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Initialize repository
            new InitCommand().execute();

            // Execute LogCommand without commits
            LogCommand logCommand = new LogCommand();
            logCommand.execute();

            String output = outContent.toString();
            assertTrue(output.contains("No commits yet."));

        } finally {
            System.setProperty("user.dir", originalDir);
            System.setOut(originalOut);
        }
    }
}
