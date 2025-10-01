// BufferedIOExample.java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BufferedIOExample {
    public static void writeBuffered(String filename, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(content);
            bw.newLine();
            bw.write("Second line for demonstration.");
            System.out.println("Buffered write to " + filename + " complete.");
        } catch (IOException e) {
            System.err.println("Write error: " + e.getMessage());
        }
    }

    public static void readBuffered(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println("Contents of " + filename + ":");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Read error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String filename = "buffered_example.txt";
        writeBuffered(filename, "Buffered I/O example with BufferedWriter");
        readBuffered(filename);
    }
}
