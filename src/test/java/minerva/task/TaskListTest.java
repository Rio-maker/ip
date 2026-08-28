package minerva.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * tests for task-list
 */
public class TaskListTest {
    /**
     * checks basic add and delete tasks functions in task list
     */
    @Test
    public void delete_validIndex_success() {
        TaskList tasks = new TaskList();
        toDo todo = new toDo("buy milk");
        tasks.add(todo);

        Task removed = tasks.delete(0);
        assertEquals(todo, removed);
        assertEquals(0, tasks.getSize());
    }

    /**
     * checks if indexOutofBoundsException is thrown when an out of index deletion is requested
     */
    @Test
    public void delete_outOfBoundsIndex_exceptionThrown() {
        TaskList tasks = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> tasks.delete(0));
    }
}