package minerva.ui;

import java.util.Scanner;

/** UI class to handle utility operations on chatbot initialization
 * Initializes scanner, banner, contains recurring prompts and farewell
 * And also display error/message code
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Just initializes scanner object to read standard user input
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next-line inputted by the user
     * @returns as a String the command inputted
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints the ASCII art banner on start up
     * Welcome message and generic help printed as well
     */
    public void showWelcome() {
        String banner = "$$\\      $$\\       $$\\                                                                         $$$$$$\\  \n"
                + "$$$\\    $$$ |      \\__|                                                                       $$  __$$\\ \n"
                + "$$$$\\  $$$$ |      $$\\       $$$$$$$\\         $$$$$$\\         $$$$$$\\        $$\\    $$\\       $$ /  $$ |\n"
                + "$$\\$$\\$$ $$ |      $$ |      $$  __$$\\       $$  __$$\\       $$  __$$\\       \\$$\\  $$  |      $$$$$$$$ |\n"
                + "$$ \\$$$  $$ |      $$ |      $$ |  $$ |      $$$$$$$$ |      $$ |  \\__|       \\$$\\$$  /       $$  __$$ |\n"
                + "$$ |\\$  /$$ |      $$ |      $$ |  $$ |      $$   ____|      $$ |              \\$$$  /        $$ |  $$ |\n"
                + "$$ | \\_/ $$ |      $$ |      $$ |  $$ |      \\$$$$$$$\\       $$ |               \\$  /         $$ |  $$ |\n"
                + "\\__|     \\__|      \\__|      \\__|  \\__|       \\_______|      \\__|                \\_/          \\__|  \\__|\n";
        System.out.println(banner);
        System.out.println("Hello! I'm minerva.Minerva! Ask me anything. *Type help for list of commands");
    }

    /**
     * Prints farewell message, only when user inputs "bye"
     */
    public void showFarewell() {
        System.out.println("Bye. And I hope to see you again soon!");
    }

    /**
     * recurring prompt asking user for input to operate on tasklist
     */
    public void showPrompt() {
        System.out.println("What can I do for you today :)? \nAwaiting your input: ");
    }

    /**
     * prints loading error/when first initializing bot startup
     */
    public void showLoadingError() {
        System.out.println("No saved tasks file found or error loading file. Starting with an empty task list.");
    }

    /**
     * Prints out a custom error message to the console
     * @param message to be passed in
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Prints out a custom message to show in the console
     * @param message to be passed in
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
}
