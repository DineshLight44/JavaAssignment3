// ListFilesInDirectory.java
import java.io.File;
import java.util.Scanner;

public class ListFilesInDirectory {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter directory path: ");
        String path = sc.nextLine().trim();
        sc.close();

        File dir = new File(path);
        if (!dir.exists()) {
            System.err.println("The path does not exist: " + path);
            return;
        }
        if (!dir.isDirectory()) {
            System.err.println("The path is not a directory: " + path);
            return;
        }

        File[] files = dir.listFiles();
        if (files == null) {
            System.err.println("Could not list files (possible I/O error or permission issue).");
            return;
        }

        System.out.printf("Files and directories in '%s':%n", path);
        for (File f : files) {
            String type = f.isDirectory() ? "<DIR>" : "<FILE>";
            System.out.printf("%-6s %s (size: %d bytes)%n", type, f.getName(), f.length());
        }
    }
}
