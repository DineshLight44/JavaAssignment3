public class CountdownWithTick {
    public static void main(String[] args) {
        Thread countdown = new Thread(() -> {
            try {
                for (int i = 10; i >= 1; i--) {
                    System.out.println("Countdown: " + i);
                    Thread.sleep(1000); // 1 second
                }
                System.out.println("Countdown finished!");
            } catch (InterruptedException e) {
                System.out.println("Countdown interrupted");
                Thread.currentThread().interrupt();
            }
        }, "Countdown");

        Thread ticker = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Tick...");
                    Thread.sleep(500); // 0.5 second
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Ticker");

        ticker.start();
        countdown.start();

        // Wait for countdown to finish and then stop the ticker
        try {
            countdown.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        ticker.interrupt();
        System.out.println("Ticker stopped after countdown completion.");
    }
}
