public class ReverseStringRunnable implements Runnable {
    private final String text;

    public ReverseStringRunnable(String text) {
        this.text = text;
    }

    @Override
    public void run() {
        // Print characters in reverse order
        for (int i = text.length() - 1; i >= 0; i--) {
            System.out.print(text.charAt(i));
            // optional small pause so we can see them one-by-one
            try { Thread.sleep(150); } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("\nReverseStringRunnable interrupted");
                break;
            }
        }
        System.out.println(); // newline after finished
    }

    public static void main(String[] args) {
        Thread t = new Thread(new ReverseStringRunnable("MULTITHREADING"), "ReversePrinter");
        t.start();
    }
}
