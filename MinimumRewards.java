import java.util.*;

public class MinimumRewards {
    
    public static int minRewards(int[] ratings) {
        int n = ratings.length;
        if (n == 0) return 0;
        
        int[] rewards = new int[n]; // Array to store rewards for each employee
        Arrays.fill(rewards, 1); // Everyone gets at least 1 reward initially
        
        // Left to right pass: If right neighbor has a higher rating, give more rewards
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                rewards[i] = rewards[i - 1] + 1;
            }
        }
        
        // Right to left pass: If left neighbor has a higher rating but fewer rewards, adjust it
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);
            }
        }
        
        // Sum up the rewards to get the minimum total required
        int totalRewards = 0;
        for (int reward : rewards) {
            totalRewards += reward;
        }
        
        return totalRewards;
    }
    
    public static void main(String[] args) {
        int[] ratings1 = {1, 0, 2};
        System.out.println("Minimum rewards needed: " + minRewards(ratings1)); // Output: 5

        int[] ratings2 = {1, 2, 2};
        System.out.println("Minimum rewards needed: " + minRewards(ratings2)); // Output: 4
    }

