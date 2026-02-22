package com.regisx001.minigit.utils;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class HashUtilTest {

    @Test
    public void testSha1() {
        String input = "hello world";
        // echo -n "hello world" | sha1sum
        // 2aae6c35c94fcfb415dbe95f408b9ce91ee846ed
        String expectedHash = "2aae6c35c94fcfb415dbe95f408b9ce91ee846ed";

        String actualHash = HashUtil.sha1(input.getBytes(StandardCharsets.UTF_8));
        assertEquals(expectedHash, actualHash);
    }
}
