package minerva.task;

/**
 * todo class extending task, with basically the exact same toString and toFileformat methods
 * just adds a T for type consistency in storage management
 */
public class toDo extends Task {
    /**
     * initializes description, exactly same as Task
     * @param description
     */
    public toDo(String description) {
        super(description);
    }
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileFormat() {
        return "T | " + super.toFileFormat();
    }
}
