import java.io.File;
import java.io.FileNotFoundException;

public class Minerva {
    private static final String FILE_PATH = "." + File.separator + "data" + File.separator + "minerva.txt";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Minerva(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            ui.showPrompt();
            String fullCommand = ui.readCommand();
            String[] part = fullCommand.split("\\s+", 2);
            Keyword keyword = switch (part[0].toLowerCase()) {
                case "bye" -> Keyword.BYE;
                case "list" -> Keyword.LIST;
                case "mark" -> Keyword.MARK;
                case "unmark" -> Keyword.UNMARK;
                case "todo" -> Keyword.TODO;
                case "deadline" -> Keyword.DEADLINE;
                case "event" -> Keyword.EVENT;
                case "delete" -> Keyword.DELETE;
                case "task" -> Keyword.TASK;
                case "help" -> Keyword.HELP;
                default -> Keyword.UNKNOWN;
            };

            switch (keyword) {
                case BYE:
                    isExit = true;
                    ui.showFarewell();
                    break;

                case LIST:
                    ui.showMessage("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.getSize(); i++) {
                        ui.showMessage((i + 1) + ". " + tasks.get(i));
                    }
                    break;

                case HELP:
                    ui.showMessage("Here are the available functions: ");
                    for (Keyword key : Keyword.values()) {
                        if (key == Keyword.UNKNOWN) {
                            continue;
                        }
                        ui.showMessage(key.toString());
                    }
                    break;

                case MARK:
                    try {
                        int taskNumber = Integer.parseInt(part[1]);
                        int index = taskNumber - 1;
                        tasks.mark(index);
                        ui.showMessage("Nice! I've marked this task as done, what's next? :\n" + tasks.get(index));
                        storage.save(tasks.getTasks());
                    } catch (IndexOutOfBoundsException | NumberFormatException e) {
                        ui.showError("mark needs a valid number less than or equals to: " + tasks.getSize());
                    }
                    break;

                case UNMARK:
                    try {
                        int taskNumber = Integer.parseInt(part[1]);
                        int index = taskNumber - 1;
                        tasks.unmark(index);
                        ui.showMessage("OK, I've marked this task as not done yet, make up your mind next time:\n" + tasks.get(index));
                        storage.save(tasks.getTasks());
                    } catch (IndexOutOfBoundsException | NumberFormatException e) {
                        ui.showError("unmark needs a valid number less than or equals to: " + tasks.getSize());
                    }
                    break;

                case TODO:
                    try {
                        String description = part[1].trim();
                        Task newTask = new toDo(description);
                        tasks.add(newTask);
                        ui.showMessage("Got it. I've added this task:\n" + newTask + "\nNow you have " + tasks.getSize() + " tasks in the list.");
                        storage.save(tasks.getTasks());
                    } catch (IndexOutOfBoundsException e) {
                        ui.showError("OOPS!!! The description of a todo cannot be empty.");
                    }
                    break;

                case DEADLINE:
                    try {
                        String[] due = part[1].split(" /by ", 2);
                        if (due.length < 2 || due[1].isBlank()) {
                            throw new MinervaArgumentException("Deadline has no due date!!!!");
                        }
                        Task newTask = new deadline(due[0], due[1]);
                        tasks.add(newTask);
                        ui.showMessage("Got it. I've added this task:\n" + newTask + "\nNow you have " + tasks.getSize() + " tasks in the list.");
                        storage.save(tasks.getTasks());
                    } catch (IndexOutOfBoundsException e) {
                        ui.showError("Deadline has missing fields!");
                    } catch (MinervaArgumentException e) {
                        ui.showError(e.getMessage());
                    } catch (java.time.format.DateTimeParseException e) {
                        ui.showError("Please enter date in yyyy-MM-dd format! (e.g., 2026-08-27)");
                    }
                    break;

                case EVENT:
                    try {
                        String[] from_to = part[1].split(" /from | /to ");
                        if (from_to.length < 3 || from_to[1].isBlank() || from_to[2].isBlank()) {
                            throw new MinervaArgumentException("Missing from or to fields, check again!");
                        }
                        Task newTask = new event(from_to[0], from_to[1], from_to[2]);
                        tasks.add(newTask);
                        ui.showMessage("Got it. I've added this task:\n" + newTask + "\nNow you have " + tasks.getSize() + " tasks in the list.");
                        storage.save(tasks.getTasks());
                    } catch (IndexOutOfBoundsException e) {
                        ui.showError("Event has missing fields!");
                    } catch (MinervaArgumentException e) {
                        ui.showError(e.getMessage());
                    } catch (java.time.format.DateTimeParseException e) {
                        ui.showError("Please enter the 'from' date in yyyy-MM-dd format! (e.g., 2026-08-27)");
                    }
                    break;

                case DELETE:
                    try {
                        int taskNumber = Integer.parseInt(part[1]);
                        int index = taskNumber - 1;
                        Task removed = tasks.delete(index);
                        ui.showMessage("Noted. I've removed this task:\n" + removed + "\nNow you have " + tasks.getSize() + " tasks in the list.");
                        storage.save(tasks.getTasks());
                    } catch (IndexOutOfBoundsException | NumberFormatException e) {
                        ui.showError("delete needs a valid number less than or equals to: " + tasks.getSize());
                    }
                    break;

                case TASK:
                    try {
                        if (fullCommand.isBlank()) {
                            throw new IllegalArgumentException();
                        }
                        Task newTask = new Task(fullCommand);
                        tasks.add(newTask);
                        ui.showMessage("added: " + fullCommand + " as Task at: " + tasks.getSize());
                        storage.save(tasks.getTasks());
                    } catch (IllegalArgumentException e) {
                        ui.showError("Sorry no blanks allowed !!");
                    }
                    break;

                case UNKNOWN:
                    ui.showError("OOPS!!! I'm sorry, but I don't know what that means :-(");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        new Minerva(FILE_PATH).run();
    }
}