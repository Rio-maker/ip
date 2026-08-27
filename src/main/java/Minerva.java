import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Minerva {
    private static int counter = 0;
    public static ArrayList<Task> taskList = new ArrayList<>();

    public static void showFarewell() {
        System.out.println("Bye. And I hope to see you again soon!");
    }

    public static void showGreeting() {
        System.out.println("Hello! I'm Minerva! Ask me anything. *Type help for list of commands");
    }

    public static void showTasklist() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < counter; i++) {
            System.out.println((i + 1) + ". " + taskList.get(i));
        }
    }

    public static void unmark(int taskNumber) {
        int index = taskNumber - 1;
        taskList.get(index).markUnDone();
        System.out.println("OK, I've marked this task as not done yet, make up your mind next time:");
        System.out.println(taskList.get(index));
        saveTasks();
    }

    public static void mark(int taskNumber) {
        int index = taskNumber - 1;
        taskList.get(index).markDone();
        System.out.println("Nice! I've marked this task as done, what's next? :");
        System.out.println(taskList.get(index));
        saveTasks();
    }

    public static void delete(int taskNumber) {
        int index = taskNumber - 1;
        String temp = taskList.get(index).toString();
        taskList.remove(index);
        System.out.println("Noted. I've removed this task:");
        System.out.println(temp);
        counter--;
        System.out.println("Now you have " + counter + " tasks in the list.");
        saveTasks();
    }

    public static void saveTasks() {
        try {
            File file = new File("." + File.separator + "data" + File.separator + "minerva.txt");
            //Overwrites the previous file if present
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs(); //mkdirs() checks whether ./data exists, creating if it doesnt.
            }
            FileWriter writer = new FileWriter(file);
            for (Task task : taskList) {
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static void loadTasks() {
        File file = new File("." + File.separator + "data" + File.separator + "minerva.txt");
        if (!file.exists()) {
            return;
        }

        try (Scanner fileScan = new Scanner(file)) {
            while (fileScan.hasNextLine()) {
                String line = fileScan.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(" \\| ");
                if (parts.length < 2) {
                    continue;
                }

                try {
                    String type = parts[0];
                    boolean isDone = parts[1].equals("1");
                    Task task = null;

                    switch (type) {
                        case "T":
                            if (parts.length >= 3) {
                                task = new toDo(parts[2]);
                            }
                            break;
                        case "D":
                            if (parts.length >= 4) {
                                task = new deadline(parts[2], parts[3]);
                            }
                            break;
                        case "E":
                            if (parts.length >= 5) {
                                task = new event(parts[2], parts[3], parts[4]);
                            }
                            break;
                        default:
                            // Generic base task fallback (format: 1 | task description)
                            task = new Task(parts[1]);
                            isDone = parts[0].equals("1");
                            break;
                    }

                    if (task != null) {
                        if (isDone) {
                            task.markDone();
                        }
                        taskList.add(task);
                        counter++;
                    }
                } catch (Exception e) {
                    System.out.println("Warning: Skipping corrupted task entry in save file.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No saved tasks file found.");
        }
    }


    public static void taskLoop(Scanner sc) {
        while (true) {
            System.out.println("What can I do for you today :)? \n" +
                    "Awaiting your input: ");
            String input = sc.nextLine();
            String[] part = input.split("\\s+", 2);
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
            if (keyword == Keyword.BYE) {
                break;
            } else if (keyword == Keyword.LIST) {
                showTasklist();
            } else if (keyword == Keyword.HELP) {
                System.out.println("Here are the available functions: ");
                for (Keyword key : Keyword.values()) {
                    System.out.println(key);
                }
            } else if (keyword == Keyword.MARK) {
                try {
                    int taskNumber = Integer.parseInt(part[1]);
                    mark(taskNumber);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("mark needs a valid number less than or equals to: " + counter);
                }
            } else if (keyword == Keyword.UNMARK) {
                try {
                    int taskNumber = Integer.parseInt(part[1]);
                    unmark(taskNumber);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("unmark needs a valid number less than or equals to: " + counter);
                }
            } else if (keyword == Keyword.TODO) {
                try {
                    String description = part[1].trim();
                    taskList.add(new toDo(description));
                    System.out.println("Got it. I've added this task:\n" + taskList.get(counter));
                    counter++;
                    System.out.println("Now you have " + counter + " tasks in the list.");
                    saveTasks(); //new
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("OOPS!!! The description of a todo cannot be empty.");
                }
            } else if (keyword == Keyword.DEADLINE) {
                try {
                    String[] due = part[1].split(" /by ", 2);
                    if (due.length < 2 || due[1].isBlank()) {
                        throw new MinervaArgumentException("Deadline has no due date!!!!");
                    }
                    taskList.add(new deadline(due[0], due[1]));
                    System.out.println("Got it. I've added this task:\n" + taskList.get(counter));
                    counter++;
                    System.out.println("Now you have " + counter + " tasks in the list.");
                    saveTasks(); //new
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Deadline has missing fields!");
                } catch (MinervaArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (java.time.format.DateTimeParseException e) {
                    System.out.println("Please enter date in yyyy-MM-dd format! (e.g., 2026-08-27)");
                }
            } else if (keyword == Keyword.EVENT) {
                try {
                    String[] from_to = part[1].split(" /from | /to ");
                    if (from_to.length < 3 || from_to[1].isBlank() || from_to[2].isBlank()) {
                        throw new MinervaArgumentException("Missing from or to fields, check again!");
                    }
                    taskList.add(new event(from_to[0], from_to[1], from_to[2]));
                    System.out.println("Got it. I've added this task:\n" + taskList.get(counter));
                    counter++;
                    System.out.println("Now you have " + counter + " tasks in the list.");
                    saveTasks(); //new
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Deadline has missing fields!");
                } catch (MinervaArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (java.time.format.DateTimeParseException e) {
                    System.out.println("Please enter the 'from' date in yyyy-MM-dd format! (e.g., 2026-08-27)");
                }
            } else if (keyword == Keyword.DELETE) {
                try {
                    int taskNumber = Integer.parseInt(part[1]);
                    delete(taskNumber);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("delete needs a valid number less than or equals to: " + counter);
                }
            } else if (keyword == Keyword.TASK) {
                try {
                    if (input.isBlank()) {
                        throw new IllegalArgumentException();
                    }
                    taskList.add(new Task(input));
                    counter++;
                    System.out.println("added: " + input + " as Task at: " + counter);
                    saveTasks(); //new
                } catch (IllegalArgumentException e) {
                    System.out.println("Sorry no blanks allowed !!");
                }
            } else if (keyword == Keyword.UNKNOWN) {
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    public static void main(String[] args) {
        String banner = "$$\\      $$\\       $$\\                                                                         $$$$$$\\  \n"
                + "$$$\\    $$$ |      \\__|                                                                       $$  __$$\\ \n"
                + "$$$$\\  $$$$ |      $$\\       $$$$$$$\\         $$$$$$\\         $$$$$$\\        $$\\    $$\\       $$ /  $$ |\n"
                + "$$\\$$\\$$ $$ |      $$ |      $$  __$$\\       $$  __$$\\       $$  __$$\\       \\$$\\  $$  |      $$$$$$$$ |\n"
                + "$$ \\$$$  $$ |      $$ |      $$ |  $$ |      $$$$$$$$ |      $$ |  \\__|       \\$$\\$$  /       $$  __$$ |\n"
                + "$$ |\\$  /$$ |      $$ |      $$ |  $$ |      $$   ____|      $$ |              \\$$$  /        $$ |  $$ |\n"
                + "$$ | \\_/ $$ |      $$ |      $$ |  $$ |      \\$$$$$$$\\       $$ |               \\$  /         $$ |  $$ |\n"
                + "\\__|     \\__|      \\__|      \\__|  \\__|       \\_______|      \\__|                \\_/          \\__|  \\__|\n";
        System.out.println(banner);
        showGreeting();
        loadTasks();
        Scanner sc = new Scanner(System.in);
        taskLoop(sc);
        showFarewell();
    }
}
