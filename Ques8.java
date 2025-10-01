public class InventorySynchronizedBlock {
    static class Inventory {
        private int stock;
        private final Object lock = new Object();

        public Inventory(int initial) {
            this.stock = initial;
        }

        // use synchronized block to protect only the critical section
        public boolean decreaseStock(int amount, String worker) {
            synchronized (lock) {
                if (stock >= amount) {
                    stock -= amount;
                    System.out.printf("%s decreased %d -> remaining %d%n", worker, amount, stock);
                    return true;
                } else {
                    System.out.printf("%s failed to decrease %d -> remaining %d%n", worker, amount, stock);
                    return false;
                }
            }
        }

        public int getStock() {
            synchronized (lock) {
                return stock;
            }
        }
    }

    public static void main(String[] args) {
        Inventory inv = new Inventory(10);

        Runnable takeTwo = () -> {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < 6; i++) {
                inv.decreaseStock(2, name);
                try { Thread.sleep(80); } catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
            }
        };

        Thread w1 = new Thread(takeTwo, "Worker-1");
        Thread w2 = new Thread(takeTwo, "Worker-2");

        w1.start(); w2.start();

        try { w1.join(); w2.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        System.out.println("Final stock: " + inv.getStock());
    }
}
