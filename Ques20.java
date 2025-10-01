// FileExistenceCheck.java
import java.io.File;
import java.io.IOException;

public class FileExistenceCheck {
    public static void main(String[] args) {
        String filename = "maybe_created.txt";
        File file = new File(filename);

        try {
            if (file.exists()) {
                System.out.println(filename + " already exists.");
            } else {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println(filename + " did not exist and was created.");
                } else {
                    System.out.println("Failed to create " + filename);
                }
            }
        } catch (IOException e) {
            System.err.println("I/O error while handling file: " + e.getMessage());
        }
    }
}
