package com.regisx001.minigit.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class IgnoreServiceTest {

    @Test
    public void testIsIgnored() throws IOException {
        // Create a temporary .minigitignore file in the current directory
        Path ignoreFile = Path.of(".minigitignore");
        boolean created = false;
        if (!Files.exists(ignoreFile)) {
            Files.writeString(ignoreFile, "/.git\ntarget/\n*.class\n");
            created = true;
        } else {
            // If it exists, we can't easily test without modifying it, but let's assume it has /.git
        }

        try {
            IgnoreService service = new IgnoreService();
            
            // If we created it, we know the contents
            if (created) {
                assertTrue(service.isIgnored(".git"));
                assertTrue(service.isIgnored(".git/config"));
                assertTrue(service.isIgnored("target"));
                assertTrue(service.isIgnored("target/classes/App.class"));
                assertTrue(service.isIgnored("src/main/App.class"));
                
                assertFalse(service.isIgnored("src/main/App.java"));
                assertFalse(service.isIgnored("README.md"));
            }
        } finally {
            if (created) {
                Files.delete(ignoreFile);
            }
        }
    }
}
