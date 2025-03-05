// Question no 1 (a)
// Description: This program solves the critical temperature problem using dynamic programming.
// It determines the minimum number of measurements required to find the exact threshold temperature.

public class CriticalTemperature {

    // Function to calculate the minimum number of measurements required
    public static int minMeasurements(int k, int n) {
        // Create a 2D array to store results for different samples (k) and temperature levels (n)
        int[][] dp = new int[k + 1][n + 1];

        // Base case: If we have only one sample, we must test each temperature level one by one
        for (int j = 1; j <= n; j++) {
            dp[1][j] = j; // With 1 sample, we need j tests to find the threshold
        }

        // Compute results for multiple samples (k) and temperature levels (n)
        for (int i = 2; i <= k; i++) {  // Loop over the number of samples
            for (int j = 1; j <= n; j++) {  // Loop over temperature levels
                dp[i][j] = Integer.MAX_VALUE; // Initialize with a very large number

                // Try different temperature levels as the first test
                for (int x = 1; x <= j; x++) {
                    // Case 1: If the material reacts at temperature x, we check lower temperatures (dp[i - 1][x - 1])
                    // Case 2: If the material does not react at x, we check higher temperatures (dp[i][j - x])
                    int worstCase = 1 + Math.max(dp[i - 1][x - 1], dp[i][j - x]);

                    // Store the minimum number of tests required in the worst case
                    dp[i][j] = Math.min(dp[i][j], worstCase);
                }
            }
        }

        // Return the minimum number of tests required for k samples and n temperature levels
        return dp[k][n];
    }

    // Main function to test the implementation
    public static void main(String[] args) {
        // Test Case 1: 1 sample, 2 temperature levels
        int k1 = 1, n1 = 2;
        int result1 = minMeasurements(k1, n1);
        System.out.println("Minimum tests for k = " + k1 + ", n = " + n1 + ": " + result1);

        // Test Case 2: 2 samples, 6 temperature levels
        int k2 = 2, n2 = 6;
        int result2 = minMeasurements(k2, n2);
        System.out.println("Minimum tests for k = " + k2 + ", n = " + n2 + ": " + result2);

        // Test Case 3: 3 samples, 6 temperature levels
        int k3 = 3, n3 = 6;
        int result3 = minMeasurements(k3, n3);
        System.out.println("Minimum tests for k = " + k3 + ", n = " + n3 + ": " + result3);
    }
}

// Summary: 
// This program uses dynamic programming to efficiently find the minimum number of tests needed 
// to determine the critical temperature level where a material reacts.
// The approach ensures that we always use the least number of tests in the worst-case scenario.
// By storing previous results in a 2D array, we avoid redundant calculations and improve performance.

// Results:
// Minimum tests for k = 1, n = 2: 2
// Minimum tests for k = 2, n = 6: 3
// Minimum tests for k = 3, n = 6: 3
