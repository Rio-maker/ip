package minerva.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {

    @Test
    public void delete_validIndex_success() {
        TaskList tasks = new TaskList();
        toDo todo = new toDo("buy milk");
        tasks.add(todo);

        Task removed = tasks.delete(0);
        assertEquals(todo, removed);
        assertEquals(0, tasks.getSize());
    }

    @Test
    public void delete_outOfBoundsIndex_exceptionThrown() {
        TaskList tasks = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> tasks.delete(0));
    }
}