package com.regisx001.minigit.cli;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.regisx001.minigit.core.Command;
import com.regisx001.minigit.core.commands.AddCommand;
import com.regisx001.minigit.core.commands.CommitCommand;
import com.regisx001.minigit.core.commands.InitCommand;

public class CommandParserTest {

    @Test
    public void testParseInit() {
        CommandParser parser = new CommandParser();
        Command cmd = parser.parse(new String[] { "init" });
        assertTrue(cmd instanceof InitCommand);
    }

    @Test
    public void testParseAdd() {
        CommandParser parser = new CommandParser();
        Command cmd = parser.parse(new String[] { "add", "file.txt" });
        assertTrue(cmd instanceof AddCommand);
    }

    @Test
    public void testParseCommit() {
        CommandParser parser = new CommandParser();
        Command cmd = parser.parse(new String[] { "commit", "-m", "message" });
        assertTrue(cmd instanceof CommitCommand);
    }

    @Test
    public void testParseFailures() {
        CommandParser parser = new CommandParser();

        // No args
        assertThrows(RuntimeException.class, () -> parser.parse(new String[] {}));

        // Unknown
        assertThrows(RuntimeException.class, () -> parser.parse(new String[] { "unknown" }));

        // Add missing file
        assertThrows(RuntimeException.class, () -> parser.parse(new String[] { "add" }));

        // Commit usage error
        assertThrows(RuntimeException.class, () -> parser.parse(new String[] { "commit", "msg" }));
    }
}
