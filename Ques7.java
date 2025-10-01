public class TicketBookingSynchronizedMethod {
    static class TicketCounter {
        private int tickets;

        public TicketCounter(int tickets) {
            this.tickets = tickets;
        }

        // synchronized method prevents two threads from booking the same ticket
        public synchronized boolean bookTicket(String user) {
            if (tickets > 0) {
                System.out.printf("%s booked ticket #%d%n", user, tickets);
                tickets--;
                return true;
            } else {
                System.out.printf("%s tried to book but tickets sold out.%n", user);
                return false;
            }
        }

        public synchronized int remaining() {
            return tickets;
        }
    }

    public static void main(String[] args) {
        TicketCounter counter = new TicketCounter(5);

        Runnable userTask = () -> {
            String name = Thread.currentThread().getName();
            while (counter.bookTicket(name)) {
                try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
            }
        };

        Thread t1 = new Thread(userTask, "User-A");
        Thread t2 = new Thread(userTask, "User-B");
        Thread t3 = new Thread(userTask, "User-C");

        t1.start(); t2.start(); t3.start();

        try {
            t1.join(); t2.join(); t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Tickets remaining: " + counter.remaining());
    }
}
