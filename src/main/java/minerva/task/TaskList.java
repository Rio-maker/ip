package minerva.task;

import java.util.ArrayList;

/**
 * taskList class to store all the tasks, just rudimentary supporting operations for tasks,minerva and storage
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * initializes tasklist if not present
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * if pre-existing tasklist, sets tasks reference to it instead of making new
     * @param tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * @return returns size of tasklist
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * @param index
     * @return task at specified index, throws out of range exception
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * adds in a new task to tasklist
     * @param task
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * mutates tasklist in place according to index passed in and deletes task
     * @param index
     * @return
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /**
     * operates on index of tasklist, marking done
     * @param index
     */
    public void mark(int index) {
        tasks.get(index).markDone();
    }
    /**
     * operates on index of tasklist, unmarking
     * @param index
     */
    public void unmark(int index) {
        tasks.get(index).markUnDone();
    }
}
