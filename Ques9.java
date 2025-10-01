public class DiningPhilosophersDeadlock {
    static class Chopstick { /* marker object */ }

    public static void main(String[] args) {
        final Chopstick left = new Chopstick();
        final Chopstick right = new Chopstick();

        Thread philosopherA = new Thread(() -> {
            synchronized (left) {
                System.out.println("Philosopher A picked up left");
                // simulate a pause so the other philosopher can pick up right
                try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                synchronized (right) {
                    System.out.println("Philosopher A picked up right — eating");
                }
            }
        }, "Philosopher-A");

        Thread philosopherB = new Thread(() -> {
            synchronized (right) {
                System.out.println("Philosopher B picked up right");
                try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                synchronized (left) {
                    System.out.println("Philosopher B picked up left — eating");
                }
            }
        }, "Philosopher-B");

        philosopherA.start();
        philosopherB.start();

        // Wait a bit and then show that both are likely blocked (deadlock)
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println("If both philosophers are waiting for the other's chopstick, a deadlock occurred.");
    }
}
