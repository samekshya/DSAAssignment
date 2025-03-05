// Question no 2 (a)
// Summary: 
// This program determines the minimum number of rewards required for employees based on performance ratings.
// It ensures that employees with higher ratings get more rewards than their lower-rated neighbors.
// The algorithm follows a two-pass greedy approach:
// 1. A left-to-right pass ensures that increasing ratings receive more rewards.
// 2. A right-to-left pass ensures fairness by adjusting rewards where needed.
// Finally, it sums up all rewards to get the minimum total required.

import java.util.*;

public class MinimumRewards {
    
    // Function to calculate the minimum number of rewards required
    public static int minRewards(int[] ratings) {
        int n = ratings.length; // Number of employees
        if (n == 0) return 0; // If there are no employees, no rewards are needed
        
        int[] rewards = new int[n]; // Array to store rewards for each employee
        Arrays.fill(rewards, 1); // Initially, give each employee at least 1 reward
        
        // Step 1: Left to right pass
        // If the right neighbor has a higher rating, they should get more rewards
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                rewards[i] = rewards[i - 1] + 1; // Give one more than the previous employee
            }
        }
        
        // Step 2: Right to left pass
        // If the left neighbor has a higher rating but fewer rewards, adjust it
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1); // Ensure fairness
            }
        }
        
        // Step 3: Calculate the total rewards needed
        int totalRewards = 0;
        for (int reward : rewards) {
            totalRewards += reward; // Sum up all the rewards
        }
        
        return totalRewards; // Return the minimum total rewards needed
    }
    
    // Main function to test the implementation
    public static void main(String[] args) {
        // Test Case 1: Employees with ratings {1, 0, 2}
        int[] ratings1 = {1, 0, 2};
        System.out.println("Minimum rewards needed: " + minRewards(ratings1)); // Expected Output: 5

        // Test Case 2: Employees with ratings {1, 2, 2}
        int[] ratings2 = {1, 2, 2};
        System.out.println("Minimum rewards needed: " + minRewards(ratings2)); // Expected Output: 4
    }
}

// Summary:
// This program efficiently distributes rewards based on employee ratings while ensuring fairness.
// The two-pass greedy approach guarantees an optimal solution with minimal computational complexity (O(n)).
// It ensures that every employee gets at least one reward and that higher-rated employees receive more than their lower-rated neighbors.

// Results:
// Minimum rewards needed: 5
// Minimum rewards needed: 4
