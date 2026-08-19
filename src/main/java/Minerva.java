import java.util.Scanner;
import java.util.Arrays;
public class Minerva {
    private static int counter = 0;
    public static String[] taskList = new String[100];
    public static void showFarewell() {
        System.out.println("Bye. And I hope to see you again soon!");
    }
    public static void showGreeting() {
        System.out.println("Hello! I'm Minerva! Ask me anything.");
    }
    public static void showTasklist() {
        for (int i = 0; i < counter; i++) {
            System.out.println((i + 1) + ". " + taskList[i]);
        }
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
            } else {
                taskList[counter] = input;
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
