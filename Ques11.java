public class AlternateOddEven {
    static class Printer {
        private boolean oddTurn = true; // start with odd

        public synchronized void printOdd(int n) throws InterruptedException {
            while (!oddTurn) wait();
            System.out.println("Odd: " + n);
            oddTurn = false;
            notify();
        }

        public synchronized void printEven(int n) throws InterruptedException {
            while (oddTurn) wait();
            System.out.println("Even: " + n);
            oddTurn = true;
            notify();
        }
    }

    public static void main(String[] args) {
        Printer p = new Printer();

        Thread oddThread = new Thread(() -> {
            try {
                for (int i = 1; i <= 19; i += 2) {
                    p.printOdd(i);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "OddThread");

        Thread evenThread = new Thread(() -> {
            try {
                for (int i = 2; i <= 20; i += 2) {
                    p.printEven(i);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "EvenThread");

        oddThread.start();
        evenThread.start();

        try { oddThread.join(); evenThread.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println("Alternating print finished.");
    }
}
