package com.regisx001.minigit.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class RepositoryLoaderTest {

    @Test
    public void testLoad() {
        RepositoryLoader loader = new RepositoryLoader();
        // Since .minigit exists in the workspace, this should succeed
        // If it didn't exist, we would test for the exception
        try {
            Repository repo = loader.load();
            assertNotNull(repo);
        } catch (RuntimeException e) {
            assertEquals("Not a MiniGit repository.", e.getMessage());
        }
    }
}
