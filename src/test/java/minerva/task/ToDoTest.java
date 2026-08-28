package minerva.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {

    @Test
    public void toString_unmarkedTask_success() {
        toDo todo = new toDo("read book");
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void toFileFormat_unmarkedTask_success() {
        toDo todo = new toDo("read book");
        assertEquals("T |  | 0 | read book", todo.toFileFormat());
    }
}
