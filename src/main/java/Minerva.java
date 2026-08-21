import java.util.*;

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
    }

    public static void mark(int taskNumber) {
        int index = taskNumber - 1;
        taskList.get(index).markDone();
        System.out.println("Nice! I've marked this task as done, what's next? :");
        System.out.println(taskList.get(index));
    }

    public static void delete(int taskNumber) {
        int index = taskNumber - 1;
        String temp = taskList.get(index).toString();
        taskList.remove(index);
        System.out.println("Noted. I've removed this task:");
        System.out.println(temp);
        counter--;
        System.out.println("Now you have " + counter + " tasks in the list.");
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
                    counter++;
                    System.out.println("Got it. I've added this task:\n" + taskList.get(counter - 1) + "\n" +
                            "Now you have " + counter + " tasks in the list.");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("OOPS!!! The description of a todo cannot be empty.");
                }
            } else if (keyword == Keyword.DEADLINE) {
                try {
                    String[] due = part[1].split(" /by ", 2);
                    if (due[1].isBlank()) {
                        throw new MinervaArgumentException("Deadline has no due date!!!!");
                    }
                    taskList.add(new deadline(due[0], due[1]));
                    System.out.println("Got it. I've added this task:\n" + taskList.get(counter));
                    counter++;
                    System.out.println("Now you have " + counter + " tasks in the list.");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Deadline has missing fields!");
                } catch (MinervaArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } else if (keyword == Keyword.EVENT) {
                try {
                    String[] from_to = part[1].split(" /from | /to ");
                    if (from_to[1].isBlank() || from_to[2].isBlank()) {
                        throw new MinervaArgumentException("Missing from or to fields, check again!");
                    }
                    taskList.add(new event(from_to[0], from_to[1], from_to[2]));
                    System.out.println("Got it. I've added this task:\n" + taskList.get(counter));
                    counter++;
                    System.out.println("Now you have " + counter + " tasks in the list.");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Deadline has missing fields!");
                } catch (MinervaArgumentException e) {
                    System.out.println(e.getMessage());
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
                } catch (IllegalArgumentException e) {
                    System.out.println("Sorry no blanks allowed !!");
                }
            } else if (keyword == Keyword.UNKNOWN) {
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
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
        taskLoop(sc);
        showFarewell();
    }
}
