package com.regisx001.minigit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * Rigorous Test :-)
     */
    @Test
    public void testApp() {
        assertDoesNotThrow(() -> App.main(new String[] {}));
    }
}
