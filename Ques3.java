public class TwoSleepThreads {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread 1");
                    Thread.sleep(1000); // 1 second
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Printer-1");

        Thread t2 = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread 2");
                    Thread.sleep(2000); // 2 seconds
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Printer-2");

        t1.start();
        t2.start();

        // Let them run for some time then stop (optional)
        try {
            Thread.sleep(8000); // main waits 8 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        t1.interrupt();
        t2.interrupt();
    }
}
