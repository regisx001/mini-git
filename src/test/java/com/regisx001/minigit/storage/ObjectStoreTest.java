package com.regisx001.minigit.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.regisx001.minigit.filesystem.FileSystemService;

public class ObjectStoreTest {

    @TempDir
    Path tempDir;

    @Test
    public void testStoreAndRead() {
        FileSystemService fs = new FileSystemService();
        Path objectsDir = tempDir.resolve("objects");
        fs.createDirectory(objectsDir);

        ObjectStore store = new ObjectStore(objectsDir, fs);

        String hash = "a1b2c3d4e5f6";
        byte[] data = "test data".getBytes();

        store.store(hash, data);

        // Verify file was created in correct location
        Path expectedFile = objectsDir.resolve("a1").resolve("b2c3d4e5f6");
        assertTrue(Files.exists(expectedFile));

        // Verify we can read it back
        byte[] readData = store.read(hash);
        assertArrayEquals(data, readData);
    }
}
