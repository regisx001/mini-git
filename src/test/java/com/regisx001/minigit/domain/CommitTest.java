package com.regisx001.minigit.domain;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CommitTest {

    @Test
    public void testSerializeWithParent() {
        Commit commit = new Commit("treeHash123", "parentHash456", "Author Name", 1600000000L, "Initial commit");

        String expected = """
                tree treeHash123
                parent parentHash456
                author Author Name 1600000000

                Initial commit""";

        assertArrayEquals(expected.getBytes(StandardCharsets.UTF_8), commit.serialize());
    }

    @Test
    public void testSerializeWithoutParent() {
        Commit commit = new Commit("treeHash123", null, "Author Name", 1600000000L, "Initial commit");

        String expected = "tree treeHash123\n" +
                "author Author Name 1600000000\n\n" +
                "Initial commit";

        assertArrayEquals(expected.getBytes(StandardCharsets.UTF_8), commit.serialize());
    }

    @Test
    public void testHash() {
        Commit commit = new Commit("treeHash123", null, "Author Name", 1600000000L, "Initial commit");
        String expectedHash = com.regisx001.minigit.utils.HashUtil.sha1(commit.serialize());
        assertEquals(expectedHash, commit.hash());
    }
}
