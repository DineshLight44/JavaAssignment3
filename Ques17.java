import java.io.FileReader;
import java.io.IOException;

public class ReadFileCharStream {
    public static void main(String[] args) {
        String filename = "example.txt"; // ensure this file exists
        try (FileReader fr = new FileReader(filename)) {
            int c;
            while ((c = fr.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
