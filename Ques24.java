// RandomAccessFileDemo.java
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileDemo {
    public static void main(String[] args) {
        String filename = "random_access_example.dat";
        File file = new File(filename);

        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            // Write initial content at the beginning
            String initial = "Hello---THIS-IS-ORIGINAL---End";
            raf.setLength(0); // clear file
            raf.write(initial.getBytes());
            System.out.println("Wrote initial: " + initial);

            // Seek to a specific position and overwrite part of it
            long overwritePos = 6; // after "Hello-"
            raf.seek(overwritePos);
            String replacement = "REPLACED";
            raf.write(replacement.getBytes());
            System.out.printf("Overwrote at position %d with '%s'%n", overwritePos, replacement);

            // Read entire file back
            raf.seek(0);
            byte[] all = new byte[(int) raf.length()];
            raf.readFully(all);
            System.out.println("Final file content: " + new String(all));
        } catch (IOException e) {
            System.err.println("RandomAccessFile error: " + e.getMessage());
        }
    }
}
