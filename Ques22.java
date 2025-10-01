// CopyFileByteStream.java
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFileByteStream {
    public static void copy(String src, String dest) throws IOException {
        try (FileInputStream fis = new FileInputStream(src);
             FileOutputStream fos = new FileOutputStream(dest)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
        }
    }

    public static void main(String[] args) {
        String src = "source.txt";
        String dest = "destination.txt";
        try {
            copy(src, dest);
            System.out.println("Copied from " + src + " to " + dest);
        } catch (IOException e) {
            System.err.println("Copy failed: " + e.getMessage());
        }
    }
}
