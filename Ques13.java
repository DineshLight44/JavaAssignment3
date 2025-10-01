// ReentrantLockCounterDemo.java
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCounterDemo {
    static class CounterNoLock {
        private int count = 0;
        public void increment() { count++; }
        public int get() { return count; }
    }

    static class CounterWithLock {
        private int count = 0;
        private final ReentrantLock lock = new ReentrantLock();
        public void increment() {
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
            }
        }
        public int get() { return count; }
    }

    public static void main(String[] args) throws InterruptedException {
        final int THREADS = 100;
        final int INCREMENTS_PER_THREAD = 1000;

        CounterNoLock noLock = new CounterNoLock();
        CounterWithLock withLock = new CounterWithLock();

        // Helper to run increments on a counter using Runnable that accepts increment function
        Runnable runNoLock = () -> {
            for (int i = 0; i < INCREMENTS_PER_THREAD; i++) noLock.increment();
        };
        Runnable runWithLock = () -> {
            for (int i = 0; i < INCREMENTS_PER_THREAD; i++) withLock.increment();
        };

        Thread[] t1 = new Thread[THREADS];
        Thread[] t2 = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            t1[i] = new Thread(runNoLock);
            t2[i] = new Thread(runWithLock);
        }

        // Start both sets
        for (int i = 0; i < THREADS; i++) { t1[i].start(); t2[i].start(); }

        // Join
        for (int i = 0; i < THREADS; i++) { t1[i].join(); t2[i].join(); }

        int expected = THREADS * INCREMENTS_PER_THREAD;
        System.out.println("Expected final count: " + expected);
        System.out.println("No-lock final count : " + noLock.get());
        System.out.println("With-lock final count: " + withLock.get());
    }
}
