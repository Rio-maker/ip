package minerva.storage;

import minerva.task.Task;
import minerva.task.toDo;
import minerva.task.deadline;
import minerva.task.event;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * storage class takes in filePath and reconstructs tasklist from filepath iteratively
 * handles loading and saving with the format described in ip
 */
public class Storage {
    private final String filePath;

    /**
     * sets up filePath according to arg
     * @param filePath
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * checks if file exists at filepath, if exists loads it in with scanner
     * scanning line by line and rebuilding arraylist of previous tasks
     * skips empty lines, corrupted lines etc, switch case for each sub-type of class
     * @return rebuilt loadedtask arraylist from memory
     * @throws FileNotFoundException minerva class handles this, and just makes a new tasklist
     */
    public ArrayList<Task> load() throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        ArrayList<Task> loadedTasks = new ArrayList<>();
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
                            task = new Task(parts[1]);
                            isDone = parts[0].equals("1");
                            break;
                    }

                    if (task != null) {
                        if (isDone) {
                            task.markDone();
                        }
                        loadedTasks.add(task);
                    }
                } catch (Exception e) {
                    System.out.println("Warning: Skipping corrupted task entry in save file.");
                }
            }
        }
        return loadedTasks;
    }

    /**
     * save runs after adding anything to tasklist/editing it
     * converts each task into file format to write into filepath line by line
     * @param tasks
     */
    public void save(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            FileWriter writer = new FileWriter(file);
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
