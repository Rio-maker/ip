public class event extends Task {
    private String from;
    private String to;

    public event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() +  " (from: " + from + " to: " + to + ")";
    }
}
