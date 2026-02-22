package com.regisx001.minigit.filesystem;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileSystemServiceTest {

    @TempDir
    Path tempDir;

    @Test
    public void testCreateDirectory() {
        FileSystemService fs = new FileSystemService();
        Path newDir = tempDir.resolve("newDir");

        fs.createDirectory(newDir);
        assertTrue(Files.exists(newDir));
        assertTrue(Files.isDirectory(newDir));
    }

    @Test
    public void testCreateFile() {
        FileSystemService fs = new FileSystemService();
        Path newFile = tempDir.resolve("newFile.txt");

        fs.createFile(newFile);
        assertTrue(Files.exists(newFile));
        assertTrue(Files.isRegularFile(newFile));
    }

    @Test
    public void testWriteAndReadLines() {
        FileSystemService fs = new FileSystemService();
        Path file = tempDir.resolve("lines.txt");

        List<String> lines = Arrays.asList("line1", "line2", "line3");
        fs.writeLines(file, lines);

        List<String> readLines = fs.readLines(file);
        assertEquals(lines, readLines);
    }

    @Test
    public void testWriteAndReadBytes() {
        FileSystemService fs = new FileSystemService();
        Path file = tempDir.resolve("bytes.bin");

        byte[] data = new byte[] { 1, 2, 3, 4, 5 };
        fs.writeBytes(file, data);

        byte[] readData = fs.readBytes(file);
        assertArrayEquals(data, readData);
    }

    @Test
    public void testExists() {
        FileSystemService fs = new FileSystemService();
        Path file = tempDir.resolve("exists.txt");

        assertFalse(fs.exists(file));
        fs.createFile(file);
        assertTrue(fs.exists(file));
    }
}
