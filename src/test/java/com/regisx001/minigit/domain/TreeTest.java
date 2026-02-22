package com.regisx001.minigit.domain;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TreeTest {

    @Test
    public void testSerialize() {
        TreeEntry entry1 = new TreeEntry("100644", "file1.txt", "hash1");
        TreeEntry entry2 = new TreeEntry("040000", "dir1", "hash2");
        List<TreeEntry> entries = Arrays.asList(entry1, entry2);

        Tree tree = new Tree(entries);

        String expectedBody = "100644 file1.txt hash1\n040000 dir1 hash2\n";
        String expectedHeader = "tree " + expectedBody.length() + "\0";
        byte[] expectedBytes = (expectedHeader + expectedBody).getBytes(StandardCharsets.UTF_8);

        assertArrayEquals(expectedBytes, tree.serialize());
    }

    @Test
    public void testHash() {
        TreeEntry entry = new TreeEntry("100644", "file.txt", "hash");
        Tree tree = new Tree(Arrays.asList(entry));

        String expectedBody = "100644 file.txt hash\n";
        String expectedHeader = "tree " + expectedBody.length() + "\0";
        byte[] expectedBytes = (expectedHeader + expectedBody).getBytes(StandardCharsets.UTF_8);

        // We can just verify it hashes the serialized bytes correctly
        String expectedHash = com.regisx001.minigit.utils.HashUtil.sha1(expectedBytes);
        assertEquals(expectedHash, tree.hash());
    }
}
