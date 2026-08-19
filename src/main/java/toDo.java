public class toDo extends Task{
    public toDo(String description) {
        super(description);
    }
    @Override
    public String toString() {
        return "[T]"+ super.toString();
    }
}
