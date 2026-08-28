package minerva.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Event class, with dataTime support parsing locate date
 */
public class event extends Task {
    protected LocalDate from;
    protected String to;

    /**
     * initalizes new event
     * @param description
     * @param from
     * @param to
     * @throws DateTimeParseException for when from entered is not valid DateTime
     */
    public event(String description, String from, String to) throws DateTimeParseException {
        super(description);
        this.from = LocalDate.parse(from.trim());
        this.to = to.trim();
    }
    /**
     * functions the same as Task just stores from and to
     * @return file-consistent format
     */
    @Override
    public String toFileFormat() {
        return "E | " + super.toFileFormat() + " | " + from.toString() + " | " + to;
    }

    /**
     * rearranges dateTime returns event version of toString
     * @return
     */
    @Override
    public String toString() {
        String formattedFrom = from.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        return "[E]" + super.toString() + " (from: " + formattedFrom + " to: " + to + ")";
    }
}
