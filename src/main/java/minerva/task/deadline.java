package minerva.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * deadline class extending task, with datetime support
 */
public class deadline extends Task {
    protected LocalDate by;
    public deadline(String description, String by) throws DateTimeParseException {
        super(description);
        this.by = LocalDate.parse(by.trim());
    }

    public LocalDate getBy() {
        return this.by;
    }

    /**
     * identical to task just with date time support
     * @return deadline variant of toString
     */
    @Override
    public String toString() {
        String formattedDate = by.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        return "[D]"+ super.toString() + " (by: " + formattedDate + ")";
    }

    /**
     * in file friendly format for storage
     * @return deadline in file consistent storage and read
     */
    @Override
    public String toFileFormat() {
        return "D | " + super.toFileFormat() + " | " + by.toString();
    }
}
