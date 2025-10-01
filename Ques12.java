public class DownloadStopFlag {
    static class Downloader implements Runnable {
        // volatile so changes are visible across threads
        private volatile boolean running = true;
        private int chunksDownloaded = 0;

        public void stop() {
            running = false;
        }

        @Override
        public void run() {
            try {
                while (running && chunksDownloaded < 20) {
                    chunksDownloaded++;
                    System.out.println("Downloading chunk " + chunksDownloaded);
                    // simulate time to download a chunk
                    Thread.sleep(300);
                }
                if (!running) {
                    System.out.println("Download stopped gracefully at chunk " + chunksDownloaded);
                } else {
                    System.out.println("Download completed: " + chunksDownloaded + " chunks");
                }
            } catch (InterruptedException e) {
                System.out.println("Downloader interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Downloader dl = new Downloader();
        Thread dlThread = new Thread(dl, "Downloader");
        dlThread.start();

        // Let it download some chunks
        Thread.sleep(1500);

        // Ask downloader to stop gracefully
        System.out.println("Main: Requesting stop...");
        dl.stop();

        // Optionally interrupt if waiting/sleeping (not strictly necessary here)
        dlThread.interrupt();

        dlThread.join();
        System.out.println("Main: downloader thread has exited.");
    }
}
