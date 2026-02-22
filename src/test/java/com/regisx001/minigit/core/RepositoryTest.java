package com.regisx001.minigit.core;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class RepositoryTest {

    @Test
    public void testRepositoryPaths() {
        Path root = Path.of(".minigit");
        Repository repo = new Repository(root);

        assertEquals(root, repo.root());
        assertEquals(root.resolve("objects"), repo.objectsDir());
        assertEquals(root.resolve("refs/heads"), repo.refsHeadsDir());
        assertEquals(root.resolve("HEAD"), repo.headFile());
        assertEquals(root.resolve("index"), repo.indexFile());
        assertEquals(root.resolve("refs/heads/main"), repo.mainBranch());
    }
}
