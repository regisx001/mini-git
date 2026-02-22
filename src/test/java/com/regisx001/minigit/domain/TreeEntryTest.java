package com.regisx001.minigit.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TreeEntryTest {

    @Test
    public void testSerialize() {
        TreeEntry entry = new TreeEntry("100644", "README.md", "a1b2c3d4e5f6");
        assertEquals("100644 README.md a1b2c3d4e5f6", entry.serialize());
    }
}
