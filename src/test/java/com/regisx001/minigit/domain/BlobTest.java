package com.regisx001.minigit.domain;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BlobTest {

    @Test
    public void testBlobSerialize() {
        String content = "hello";
        Blob blob = new Blob(content.getBytes(StandardCharsets.UTF_8));

        byte[] expected = "blob 5\0hello".getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expected, blob.serialize());
    }

    @Test
    public void testBlobHash() {
        String content = "hello";
        Blob blob = new Blob(content.getBytes(StandardCharsets.UTF_8));

        // echo -ne "blob 5\0hello" | sha1sum
        // b6fc4c620b67d95f953a5c1c1230aaab5db5a1b0
        String expectedHash = "b6fc4c620b67d95f953a5c1c1230aaab5db5a1b0";
        assertEquals(expectedHash, blob.hash());
    }
}
