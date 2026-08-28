package minerva.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for todo class
 */
public class ToDoTest {
    /**
     * initializes new todo with description, checks if same as expected, and subsequently tests Task's toString
     */
    @Test
    public void toString_unmarkedTask_success() {
        toDo todo = new toDo("read book");
        assertEquals("[T][ ] read book", todo.toString());
    }

    /**
     * checks if todo's to file format and subsequently task's file format is as expected for storage
     */
    @Test
    public void toFileFormat_unmarkedTask_success() {
        toDo todo = new toDo("read book");
        assertEquals("T |  | 0 | read book", todo.toFileFormat());
    }
}
