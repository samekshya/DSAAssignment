// Question no 4 (a)
// Summary:
// This program finds the **top 3 trending hashtags** in February 2024 from a list of tweets.
// - It extracts hashtags from tweets and counts their occurrences.
// - The hashtags are **sorted by frequency** (higher first) and **alphabetically** in case of ties.
// - Finally, it returns the **top 3** most used hashtags.

import java.util.*;

public class TrendingHashtags {

    // Function to find the top 3 trending hashtags from a list of tweets
    public static List<String> topTrendingHashtags(List<String> tweets) {
        Map<String, Integer> hashtagCount = new HashMap<>(); // Stores hashtag counts
        
        // Step 1: Extract hashtags and count occurrences
        for (String tweet : tweets) {
            String[] words = tweet.split(" "); // Split tweet into words
            for (String word : words) {
                if (word.startsWith("#")) { // Check if the word is a hashtag
                    hashtagCount.put(word, hashtagCount.getOrDefault(word, 0) + 1); // Count the hashtag
                }
            }
        }

        // Step 2: Sort hashtags by frequency (descending), then alphabetically
        List<Map.Entry<String, Integer>> sortedHashtags = new ArrayList<>(hashtagCount.entrySet());
        sortedHashtags.sort((a, b) -> 
            b.getValue().equals(a.getValue()) ? a.getKey().compareTo(b.getKey()) : b.getValue() - a.getValue()
        );

        // Step 3: Get the top 3 hashtags
        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(3, sortedHashtags.size()); i++) {
            result.add(sortedHashtags.get(i).getKey());
        }

        return result; // Return the top 3 trending hashtags
    }

    public static void main(String[] args) {
        // Example tweets containing hashtags
        List<String> tweets = Arrays.asList(
            "#HappyDay is here!",
            "Loving this #TechLife",
            "Work hard, play hard. #WorkLife",
            "#HappyDay with family",
            "#TechLife innovations are great!",
            "Feeling great on this #HappyDay"
        );

        // Print the top 3 trending hashtags
        System.out.println("Top 3 Trending Hashtags: " + topTrendingHashtags(tweets));
    }
}

// Summary:
// - The program extracts hashtags from tweets and counts how often they appear.
// - Hashtags are sorted by **frequency (descending)** and **alphabetically** in case of ties.
// - The top 3 most frequent hashtags are selected and returned.
// - Uses a **HashMap** for counting and **sorting by custom logic** for ranking.

// Expected Output:
// Top 3 Trending Hashtags: [#HappyDay, #TechLife, #WorkLife]
