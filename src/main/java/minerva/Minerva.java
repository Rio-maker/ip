package minerva;

import java.io.File;
import java.io.FileNotFoundException;

import minerva.common.Keyword;
import minerva.exception.MinervaArgumentException;
import minerva.storage.Storage;
import minerva.task.Deadline;
import minerva.task.Event;
import minerva.task.Task;
import minerva.task.TaskList;
import minerva.task.ToDo;
import minerva.ui.Ui;

/**
 * Minerva class to take in a filepath, check for and load any existing old storage
 * runs task loop after once tasklist taken care of
 */
public class Minerva {
    private static final String FILE_PATH = "." + File.separator + "data" + File.separator + "minerva.txt";
    private Storage storage;
    private TaskList tasks;
    private TaskList foundTasks;
    private Ui ui;

    /**
     * Initializes a new UI object to handle user interactions
     * Checks for last-used storage in filepath to load in TaskList conversion
     * @param filePath the file path to be used for checking old storage
     */
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

    /**
     * starts up with welcome message, initialize loop to continuously prompt user
     * isExit turns true only for bye command and loop is exited.
     * keyword switching based on first word used in user-reply
     * delete, mark and unmark has index handling, doesn't check for over-marking/unmarking
     * todo,deadline,event all has formatting checks in place
     * for level-9, finding, does not store the found sub-list in future, didnt see a point
     */
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
                case "find" -> Keyword.FIND;
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

                case FIND:
                    String word = part[1].trim();
                    ui.showMessage("Here are the matching tasks in your list:");
                    ui.showMessage("Please do not delete/mark/unmark with this found index");
                    foundTasks = new TaskList();
                    for (int i = 0; i < tasks.getSize(); i++) {
                        if (tasks.get(i).containsWord(word)) {
                            foundTasks.add(tasks.get(i));
                        }
                    }
                    for (int i = 0; i < foundTasks.getSize(); i++) {
                        ui.showMessage((i + 1) + "." + foundTasks.get(i));
                    }
                    break;

                case HELP:
                    ui.showMessage("Here are the available functions: ");
                    Keyword[] keywords = Keyword.values();
                    for (int i = 0; i < keywords.length; i++){
                        Keyword key = keywords[i];
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
                        ui.showMessage("Nice! I've marked this task as done, what's next? :\n"
                                + tasks.get(index));
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
                        ui.showMessage("OK, I've marked this task as not done yet, make up your mind next time:\n"
                                + tasks.get(index));
                        storage.save(tasks.getTasks());
                    } catch (IndexOutOfBoundsException | NumberFormatException e) {
                        ui.showError("unmark needs a valid number less than or equals to: " + tasks.getSize());
                    }
                    break;

                case TODO:
                    try {
                        String description = part[1].trim();
                        Task newTask = new ToDo(description);
                        tasks.add(newTask);
                        ui.showMessage("Got it. I've added this task:\n" + newTask
                                + "\nNow you have " + tasks.getSize() + " tasks in the list.");
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
                        Task newTask = new Deadline(due[0], due[1]);
                        tasks.add(newTask);
                        ui.showMessage("Got it. I've added this task:\n" + newTask
                                + "\nNow you have " + tasks.getSize() + " tasks in the list.");
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
                        String[] fromTo = part[1].split(" /from | /to ");
                        if (fromTo.length < 3 || fromTo[1].isBlank() || fromTo[2].isBlank()) {
                            throw new MinervaArgumentException("Missing from or to fields, check again!");
                        }
                        Task newTask = new Event(fromTo[0], fromTo[1], fromTo[2]);
                        tasks.add(newTask);
                        ui.showMessage("Got it. I've added this task:\n" + newTask
                                + "\nNow you have " + tasks.getSize() + " tasks in the list.");
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
                        ui.showMessage("Noted. I've removed this task:\n" + removed
                                + "\nNow you have " + tasks.getSize() + " tasks in the list.");
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
                        ui.showMessage("added: " + fullCommand + " as Task at index: " + tasks.getSize());
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

    /**
     * main method used to just intialize class and run task loop
     * @param args boilerplate
     */
    public static void main(String[] args) {
        new Minerva(FILE_PATH).run();
    }
}
