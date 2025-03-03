// Question no 6 b
// Description: Implements a multi-threaded web crawler.
// Uses multiple threads to fetch and process web pages concurrently.

import java.util.concurrent.*;
import java.util.*;

class WebCrawler {
    private final ExecutorService executor;
    private final Set<String> visitedUrls;
    private final Queue<String> urlQueue;

    public WebCrawler(int threadCount) {
        this.executor = Executors.newFixedThreadPool(threadCount);
        this.visitedUrls = Collections.synchronizedSet(new HashSet<>());
        this.urlQueue = new ConcurrentLinkedQueue<>();
    }

    public void addUrl(String url) {
        if (!visitedUrls.contains(url)) {
            urlQueue.add(url);
        }
    }

    public void crawl() {
        while (!urlQueue.isEmpty()) {
            String url = urlQueue.poll();
            if (url != null && visitedUrls.add(url)) {
                executor.submit(() -> fetchPage(url));
            }
        }
        shutdown();
    }

    private void fetchPage(String url) {
        // Simulate fetching the web page
        System.out.println("Crawling: " + url);
        // Here, we could parse the page and extract more URLs
    }

    private void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler(4);
        crawler.addUrl("https://example.com");
        crawler.addUrl("https://example.org");
        crawler.addUrl("https://example.net");
        crawler.crawl();
    }
}
