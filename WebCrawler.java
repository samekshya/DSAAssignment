// Question no 6 (b)
// Description:
// This program implements a **multi-threaded web crawler** that fetches and processes web pages concurrently.
// - It uses a **thread pool** to manage multiple threads efficiently.
// - A **queue** is used to store URLs to be processed, ensuring a breadth-first-like approach.
// - A **set** is used to keep track of visited URLs, preventing duplicate crawling.
// - Each thread fetches a web page and processes it independently.

import java.util.concurrent.*;
import java.util.*;

class WebCrawler {
    private final ExecutorService executor; // Thread pool for concurrent crawling
    private final Set<String> visitedUrls; // Stores already visited URLs
    private final Queue<String> urlQueue; // Stores URLs yet to be processed

    // Constructor to initialize the web crawler with a fixed number of threads
    public WebCrawler(int threadCount) {
        this.executor = Executors.newFixedThreadPool(threadCount); // Thread pool with a fixed number of threads
        this.visitedUrls = Collections.synchronizedSet(new HashSet<>()); // Synchronized set to prevent duplicate visits
        this.urlQueue = new ConcurrentLinkedQueue<>(); // Thread-safe queue for URLs
    }

    // Adds a URL to the queue if it has not been visited yet
    public void addUrl(String url) {
        if (!visitedUrls.contains(url)) {
            urlQueue.add(url);
        }
    }

    // Starts the crawling process, using multiple threads to process URLs
    public void crawl() {
        while (!urlQueue.isEmpty()) { // Process all URLs in the queue
            String url = urlQueue.poll(); // Get the next URL from the queue
            if (url != null && visitedUrls.add(url)) { // Check if the URL was not visited before
                executor.submit(() -> fetchPage(url)); // Submit a new crawling task to the thread pool
            }
        }
        shutdown(); // Once all URLs are processed, shut down the thread pool
    }

    // Simulates fetching a web page (this is where actual HTTP requests would be made)
    private void fetchPage(String url) {
        System.out.println("Crawling: " + url);
        // In a real-world scenario, this method would fetch the page content and extract more URLs
    }

    // Gracefully shuts down the thread pool after crawling is complete
    private void shutdown() {
        executor.shutdown(); // Initiate shutdown
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) { // Wait for active tasks to finish
                executor.shutdownNow(); // Force shutdown if tasks are still running
            }
        } catch (InterruptedException e) {
            executor.shutdownNow(); // Ensure complete shutdown
        }
    }

    public static void main(String[] args) {
        // Create a web crawler with 4 concurrent threads
        WebCrawler crawler = new WebCrawler(4);

        // Add seed URLs to begin crawling
        crawler.addUrl("https://example.com");
        crawler.addUrl("https://example.org");
        crawler.addUrl("https://example.net");

        // Start crawling process
        crawler.crawl();
    }
}

// Summary:
// - Uses **multi-threading** to crawl web pages concurrently, improving efficiency.
// - Uses **a thread pool** (ExecutorService) to manage multiple crawling tasks at the same time.
// - **Prevents duplicate visits** by storing already visited URLs in a **synchronized set**.
// - **Ensures safe queue access** with a **ConcurrentLinkedQueue** for thread-safe URL processing.
// - **Gracefully shuts down** after crawling is completed.

// Expected Output:
// Crawling: https://example.net
// Crawling: https://example.org
// Crawling: https://example.com
