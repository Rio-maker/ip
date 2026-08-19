public class deadline extends Task{
    protected String by;
    public deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]"+ super.toString() + " (by: " + by + ")";
    }
}
