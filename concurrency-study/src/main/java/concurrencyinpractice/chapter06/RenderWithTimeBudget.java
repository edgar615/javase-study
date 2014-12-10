package concurrencyinpractice.chapter06;

import java.util.Random;
import java.util.concurrent.*;

public class RenderWithTimeBudget {
    private static final Ad DEFAULT_AD = new Ad("default");
    private static final long TIME_BUDGET = 1000;
    private static final ExecutorService exec = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {
        Page page = new RenderWithTimeBudget().renderPageWithAd();
        page.printAd();
    }

    Page renderPageWithAd() throws InterruptedException {
        long endNanos = System.currentTimeMillis() + TIME_BUDGET;
        Future<Ad> f = exec.submit(new FetchAdTask());
        // Render the page while waiting for the ad
        Page page = renderPageBody();
        Ad ad;
        try {
            // Only wait for the remaining time budget
            long timeLeft = endNanos - System.currentTimeMillis();
            ad = f.get(timeLeft, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            ad = DEFAULT_AD;
        } catch (TimeoutException e) {
            ad = DEFAULT_AD;
            f.cancel(true);
        } finally {
            exec.shutdown();
        }

        page.setAd(ad);
        return page;
    }

    Page renderPageBody() { return new Page(); }


    static class Ad {
        private String name;

        Ad(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class Page {
        private Ad ad;
        public void setAd(Ad ad) {
            this.ad = ad;
        }

        public void printAd() {
            System.out.println(ad.getName());
        }
    }

    static class FetchAdTask implements Callable<Ad> {
        public Ad call() throws InterruptedException {
            int rand =new Random().nextInt(2000);
            TimeUnit.MILLISECONDS.sleep(rand);
            return new Ad("ad");
        }
    }

}