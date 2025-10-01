public class WorkerPriorityDemo {
    public static void main(String[] args) {
        Runnable workerTask = () -> {
            String name = Thread.currentThread().getName();
            int prio = Thread.currentThread().getPriority();
            for (int i = 1; i <= 5; i++) {
                System.out.printf("[%s | priority=%d] step %d%n", name, prio, i);
                try { Thread.sleep(200); } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.printf("[%s] done%n", name);
        };

        Thread w1 = new Thread(workerTask, "Worker-1");
        Thread w2 = new Thread(workerTask, "Worker-2");
        Thread w3 = new Thread(workerTask, "Worker-3");

        // Assign different priorities (valid range: 1..10)
        w1.setPriority(Thread.MIN_PRIORITY);        // 1
        w2.setPriority(Thread.NORM_PRIORITY);       // 5
        w3.setPriority(Thread.MAX_PRIORITY);        // 10

        w1.start();
        w2.start();
        w3.start();

        // Wait for all to finish
        try {
            w1.join();
            w2.join();
            w3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("All workers finished.");
    }
}
