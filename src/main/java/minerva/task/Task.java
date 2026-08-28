package minerva.task;

/**
 * Task class, not abstract, although todo,event and deadline follow and override a lot
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * initializes new task
     * @param description description to be used when storing and for toString
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Based on boolean of isDone
     * @return either X or blank for toString method
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * updates boolean so done status will return X
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * updates boolean so done status returns blank
     */
    public void markUnDone() {
        this.isDone = false;
    }

    /**
     * toString method, applies almost exactly to every sub-task, just different formatting
     * @return returning in [doneStatus] description
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * for storage class file saving
     * @return in a file consistent format for saving, and also easier for next read
     */
    public String toFileFormat() {
        return " | " + (isDone? "1" : "0") + " | " + description;
    }
}

