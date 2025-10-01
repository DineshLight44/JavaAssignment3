// WriteFileByteStream.java
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteFileByteStream {
    public static void main(String[] args) {
        String filename = "output.txt";
        String content = "Java I/O Streams Example";
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(content.getBytes()); // platform default charset
            System.out.println("Wrote to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
