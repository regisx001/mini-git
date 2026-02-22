package com.regisx001.minigit.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.regisx001.minigit.core.Repository;
import com.regisx001.minigit.filesystem.FileSystemService;

public class RefStoreTest {

    @TempDir
    Path tempDir;

    @Test
    public void testRefStore() throws IOException {
        FileSystemService fs = new FileSystemService();
        Path repoRoot = tempDir.resolve(".minigit");
        fs.createDirectory(repoRoot);

        Repository repo = new Repository(repoRoot);

        // Setup HEAD and refs/heads/main
        Path headFile = repo.headFile();
        fs.createFile(headFile);
        fs.writeFile(headFile, "refs/heads/main\n");

        Path refsHeadsDir = repo.refsHeadsDir();
        fs.createDirectory(refsHeadsDir);

        Path mainBranch = repo.mainBranch();
        fs.createFile(mainBranch);
        fs.writeFile(mainBranch, ""); // Empty initially

        RefStore refStore = new RefStore(repo, fs);

        assertEquals("refs/heads/main", refStore.readHEAD());
        assertEquals("main", refStore.readCurrentBranch());
        assertNull(refStore.readCurrentCommit());

        // Update commit
        String newCommitHash = "a1b2c3d4e5f6";
        refStore.updateCurrentCommit(newCommitHash);

        assertEquals(newCommitHash, refStore.readCurrentCommit());
        assertEquals(newCommitHash, Files.readString(mainBranch).trim());
    }
}
