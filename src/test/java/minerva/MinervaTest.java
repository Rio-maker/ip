package minerva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Tests command processing in Minerva. */
public class MinervaTest {
    @TempDir
    private Path temporaryDirectory;

    @Test
    public void processCommand_emptyInput_returnsFriendlyMessage() {
        Minerva minerva = createMinerva();

        assertEquals("No worries! Please enter a command so I can help.",
                minerva.processCommand("   "));
    }

    @Test
    public void processCommand_addAndList_containsAddedTask() {
        Minerva minerva = createMinerva();

        minerva.processCommand("todo read book");

        assertTrue(minerva.processCommand("list").contains("[T][ ] read book"));
    }

    @Test
    public void processCommand_unknownCommand_returnsHelpfulMessage() {
        Minerva minerva = createMinerva();

        assertEquals(
                "I’m sorry, I didn’t recognize that command. Type help to see what I can do.",
                minerva.processCommand("unknown"));
    }

    @Test
    public void processCommand_undoAfterAdd_removesAddedTask() {
        Minerva minerva = createMinerva();

        minerva.processCommand("todo read book");
        minerva.processCommand("undo");

        assertEquals("Here are the tasks in your list:", minerva.processCommand("list"));
    }

    private Minerva createMinerva() {
        return new Minerva(temporaryDirectory.resolve("minerva.txt").toString());
    }
}
