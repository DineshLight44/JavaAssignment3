// WriteFileCharStream.java
import java.io.FileWriter;
import java.io.IOException;

public class WriteFileCharStream {
    public static void main(String[] args) {
        String filename = "example.txt";
        String content = "This is an example written via FileWriter.";
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(content);
            System.out.println("Written to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
