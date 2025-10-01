// DeadlockTryLockDemo.java
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class DeadlockTryLockDemo {
    private static final ReentrantLock lock1 = new ReentrantLock();
    private static final ReentrantLock lock2 = new ReentrantLock();

    // Intentionally creates deadlock: threadA acquires lock1 then lock2; threadB acquires lock2 then lock1
    static void createDeadlock() throws InterruptedException {
        Thread tA = new Thread(() -> {
            lock1.lock();
            try {
                System.out.println("T-A acquired lock1");
                sleepMillis(200);
                System.out.println("T-A waiting for lock2");
                lock2.lock(); // blocks here, deadlock possible
                try { System.out.println("T-A acquired lock2"); } finally { lock2.unlock(); }
            } finally { lock1.unlock(); }
        }, "Thread-A");

        Thread tB = new Thread(() -> {
            lock2.lock();
            try {
                System.out.println("T-B acquired lock2");
                sleepMillis(200);
                System.out.println("T-B waiting for lock1");
                lock1.lock(); // blocks here, deadlock possible
                try { System.out.println("T-B acquired lock1"); } finally { lock1.unlock(); }
            } finally { lock2.unlock(); }
        }, "Thread-B");

        tA.start(); tB.start();
        // Wait a little to observe deadlock
        Thread.sleep(1000);
        System.out.println("If both threads are blocked, deadlock occurred (demonstration).");
        // Interrupt threads (best-effort) to exit demo
        tA.interrupt();
        tB.interrupt();
        tA.join(200);
        tB.join(200);
    }

    // Fixed version using tryLock with timeout
    static void fixDeadlockWithTryLock() throws InterruptedException {
        Thread tA = new Thread(() -> {
            boolean acquiredLock1 = false;
            boolean acquiredLock2 = false;
            try {
                acquiredLock1 = lock1.tryLock(500, TimeUnit.MILLISECONDS);
                if (!acquiredLock1) {
                    System.out.println("T-A couldn't acquire lock1, giving up");
                    return;
                }
                System.out.println("T-A acquired lock1");
                sleepMillis(200);
                acquiredLock2 = lock2.tryLock(500, TimeUnit.MILLISECONDS);
                if (!acquiredLock2) {
                    System.out.println("T-A couldn't acquire lock2, releasing lock1 and retrying later");
                    return;
                }
                System.out.println("T-A acquired lock2 — doing work");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if (acquiredLock2) lock2.unlock();
                if (acquiredLock1) lock1.unlock();
            }
        }, "Thread-A-fixed");

        Thread tB = new Thread(() -> {
            boolean acquiredLock2 = false;
            boolean acquiredLock1 = false;
            try {
