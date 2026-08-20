import java.util.Scanner;
import java.util.Arrays;
public class Minerva {
    private static int counter = 0;
    public static Task[] taskList = new Task[100];
    public static void showFarewell() {
        System.out.println("Bye. And I hope to see you again soon!");
    }
    public static void showGreeting() {
        System.out.println("Hello! I'm Minerva! Ask me anything.");
    }
    public static void showTasklist() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < counter; i++) {
            System.out.println((i + 1) + ". " + taskList[i]);
        }
    }
    public static void unmark(int taskNumber) {
        int index = taskNumber - 1;
        taskList[index].markUnDone();
        System.out.println("OK, I've marked this task as not done yet, make up your mind next time:");
        System.out.println(taskList[index]);
    }
    public static void mark(int taskNumber) {
        int index = taskNumber - 1;
        taskList[index].markDone();
        System.out.println("Nice! I've marked this task as done, what's next? :");
        System.out.println(taskList[index]);
    }

    public static void taskLoop(Scanner sc) {
        while(true) {
            System.out.println("What can I do for you today :)?\n" +
                                "Awaiting your input: ");
            String input = sc.nextLine();
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                showTasklist();
            } else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                mark(taskNumber);
            } else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7));
                unmark(taskNumber);
            } else if (input.startsWith("todo ")) {
                try {
                    taskList[counter] = new toDo(input.substring(5));
                    counter ++;
                    System.out.println("Got it. I've added this task:\n" + taskList[counter - 1].toString()+ "\n" +
                            "Now you have " + counter + " tasks in the list.");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("OOPS!!! The description of a todo cannot be empty.");
                }
            } else if (input.startsWith("deadline ")) {
                String[] parts = input.split(" /by ", 2);
                taskList[counter] = new deadline( parts[0].substring(9), // removes "deadline "
                        parts[1]);
                System.out.println("Got it. I've added this task:\n" + taskList[counter]);
                counter++;
                System.out.println("Now you have " + counter + " tasks in the list.");
            } else if (input.startsWith("event ")) {
            String[] parts = input.split(" /from | /to ");
            taskList[counter] = new event( parts[0].substring(6), parts[1],parts[2]);
            System.out.println("Got it. I've added this task:\n" + taskList[counter]);
            counter++;
            System.out.println("Now you have " + counter + " tasks in the list.");
        }
            else {
                if (input.isBlank()) {
                    System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
                    continue;
                }
                taskList[counter] = new Task(input);
                counter ++;
                System.out.println("added: " + input);
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
