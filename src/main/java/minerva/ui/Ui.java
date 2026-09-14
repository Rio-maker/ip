package minerva.ui;

import java.util.Scanner;

/** Handles console input and output for Minerva.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Creates a scanner for reading commands from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next command entered by the user.
     *
     * @return command entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the startup banner and welcome message.
     */
    public void showWelcome() {
        //CHECKSTYLE.OFF: LineLength
        String banner = "$$\\      $$\\       $$\\                                                                         $$$$$$\\  \n"
                + "$$$\\    $$$ |      \\__|                                                                       $$  __$$\\ \n"
                + "$$$$\\  $$$$ |      $$\\       $$$$$$$\\         $$$$$$\\         $$$$$$\\        $$\\    $$\\       $$ /  $$ |\n"
                + "$$\\$$\\$$ $$ |      $$ |      $$  __$$\\       $$  __$$\\       $$  __$$\\       \\$$\\  $$  |      $$$$$$$$ |\n"
                + "$$ \\$$$  $$ |      $$ |      $$ |  $$ |      $$$$$$$$ |      $$ |  \\__|       \\$$\\$$  /       $$  __$$ |\n"
                + "$$ |\\$  /$$ |      $$ |      $$ |  $$ |      $$   ____|      $$ |              \\$$$  /        $$ |  $$ |\n"
                + "$$ | \\_/ $$ |      $$ |      $$ |  $$ |      \\$$$$$$$\\       $$ |               \\$  /         $$ |  $$ |\n"
                + "\\__|     \\__|      \\__|      \\__|  \\__|       \\_______|      \\__|                \\_/          \\__|  \\__|\n";
        //CHECKSTYLE.ON: LineLength
        System.out.println(banner);
        System.out.println("Hello! I'm minerva.Minerva! Ask me anything. *Type help for list "
                + "of commands");
    }

    /**
     * Displays the farewell message.
     */
    public void showFarewell() {
        System.out.println("Bye. And I hope to see you again soon!");
    }

    /**
     * Displays the recurring prompt for the next user command.
     */
    public void showPrompt() {
        System.out.println("What can I do for you today :)? \nAwaiting your input: ");
    }

    /**
     * Displays the message shown when no saved task file is available.
     */
    public void showLoadingError() {
        System.out.println("No saved tasks file found or error loading file. Starting with an empty task list.");
    }

    /**
     * Displays an error message on the console.
     *
     * @param message error message to display
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays a message on the console.
     *
     * @param message message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
}
