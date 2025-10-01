// ReadFileByteStream.java
import java.io.FileInputStream;
import java.io.IOException;

public class ReadFileByteStream {
    public static void main(String[] args) {
        String filename = "input.txt"; // ensure this file exists
        try (FileInputStream fis = new FileInputStream(filename)) {
            int b;
            while ((b = fis.read()) != -1) {
                // convert byte to char for display
                System.out.print((char) b);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
