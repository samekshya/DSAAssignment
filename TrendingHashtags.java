//Question no 4a
// Description: Finds the top 3 trending hashtags in February 2024.
// Extracts hashtags from a list of tweets and sorts them by frequency.

import java.util.*;

public class TrendingHashtags {
    
    // Find the top 3 trending hashtags from a list of tweets.
    public static List<String> topTrendingHashtags(List<String> tweets) {
        Map<String, Integer> hashtagCount = new HashMap<>();
        
        // Extract hashtags and count occurrences
        for (String tweet : tweets) {
            String[] words = tweet.split(" ");
            for (String word : words) {
                if (word.startsWith("#")) {
                    hashtagCount.put(word, hashtagCount.getOrDefault(word, 0) + 1);
                }
            }
        }
        
        // Sort hashtags by count (descending) and lexicographically
        List<Map.Entry<String, Integer>> sortedHashtags = new ArrayList<>(hashtagCount.entrySet());
        sortedHashtags.sort((a, b) -> b.getValue().equals(a.getValue()) ? a.getKey().compareTo(b.getKey()) : b.getValue() - a.getValue());
        
        // Get the top 3 hashtags
        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(3, sortedHashtags.size()); i++) {
            result.add(sortedHashtags.get(i).getKey());
        }
        return result;
    }

    public static void main(String[] args) {
        // Example tweets for trending hashtags
        List<String> tweets = Arrays.asList(
            "#HappyDay is here!",
            "Loving this #TechLife",
            "Work hard, play hard. #WorkLife",
            "#HappyDay with family",
            "#TechLife innovations are great!",
            "Feeling great on this #HappyDay"
        );
        
        // Print the top 3 trending hashtags
        System.out.println(topTrendingHashtags(tweets)); // Output: [#HappyDay, #TechLife, #WorkLife]
    }
}
