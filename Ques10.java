public class ProducerConsumerWaitNotify {
    static class Buffer {
        private Integer data = null; // null means empty

        public synchronized void produce(int value) throws InterruptedException {
            while (data != null) { // buffer full
                wait();
            }
            data = value;
            System.out.println("Produced: " + value);
            notify(); // wake one waiting consumer
        }

        public synchronized int consume() throws InterruptedException {
            while (data == null) { // buffer empty
                wait();
            }
            int val = data;
            data = null;
            System.out.println("Consumed: " + val);
            notify(); // wake one waiting producer
            return val;
        }
    }

    public static void main(String[] args) {
        Buffer buf = new Buffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    buf.produce(i);
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    buf.consume();
                    Thread.sleep(250);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        producer.start();
        consumer.start();

        try { producer.join(); consumer.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println("Producer-consumer finished.");
    }
}
