import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static String PATH;
    private static ArrayList<Task> tasks = new ArrayList<Task>();

    public Storage(String filePath) {
        this.PATH = filePath;
    }

    public static void checkFile() throws IOException {
        final Path p = Paths.get(PATH);
        final String DIRECTORY = p.getParent().toString();
        final String FILENAME = p.getFileName().toString();
        System.out.println(DIRECTORY);
        System.out.println(FILENAME);

        File file = new File(FILENAME);
        File dir = new File(DIRECTORY);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public static ArrayList<Task> loadTask() throws IOException {
        try {
            checkFile();
            File f = new File(PATH);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String nextLine = s.nextLine();
                String[] words = nextLine.split(", ", 0);
                String type = words[0];
                Boolean isDone = Boolean.valueOf(words[1]);
                String task = words[2];
                switch (type) {
                    case "T":
                        tasks.add(new Todo(task, isDone));
                        break;
                    case "D":
                        tasks.add(new Deadline(task, isDone));
                        break;
                    case "E":
                        tasks.add(new Event(task, isDone));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected type: " + type);
                }
            }
            System.out.println("Finished loading saved file.");
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("A new file is created!");
        }
        return tasks;
    }

    public static void saveList() {
        try {
            FileWriter fw = new FileWriter(PATH);

            for (Task t : tasks) {
                String str = t.type + ", " + t.isDone + ", " + t.description;
                fw.write(str + "\n");
            }

            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
