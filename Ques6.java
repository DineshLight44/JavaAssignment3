public class DaemonAutoSaveDemo {
    public static void main(String[] args) {
        Thread autoSave = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("Auto-Save in progress...");
                    Thread.sleep(3000); // 3 seconds
                }
            } catch (InterruptedException e) {
                System.out.println("Auto-save interrupted");
                Thread.currentThread().interrupt();
            }
        }, "AutoSaveDaemon");

        autoSave.setDaemon(true); // important: mark as daemon
        autoSave.start();

        // Simulate main thread doing file processing
        System.out.println("Main: Starting file processing...");
        for (int i = 1; i <= 5; i++) {
            System.out.printf("Main: processing file %d/5%n", i);
            try {
                Thread.sleep(1500); // simulate processing time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Main: File processing complete. Exiting main thread.");
        // Once main ends (and no non-daemon threads remain), the JVM shuts down and the daemon stops.
    }
}
