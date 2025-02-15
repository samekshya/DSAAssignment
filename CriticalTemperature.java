public class CriticalTemperature {

    // Function to calculate the minimum number of measurements required
    public static int minMeasurements(int k, int n) {
        // Create a 2D array to store results for different samples (k) and temperature levels (n)
        int[][] dp = new int[k + 1][n + 1];

        // If we have only one sample, we need to check each level one by one
        for (int j = 1; j <= n; j++) {
            dp[1][j] = j;
        }

        // Compute results for multiple samples (k) and temperature levels (n)
        for (int i = 2; i <= k; i++) {  // Loop over the number of samples
            for (int j = 1; j <= n; j++) {  // Loop over temperature levels
                dp[i][j] = Integer.MAX_VALUE; // Set the initial value to a large number

                // Try different temperature levels and find the best choice
                for (int x = 1; x <= j; x++) {
                    // If the material reacts at x, we check below x
                    // If it doesn't react, we check above x
                    int worstCase = 1 + Math.max(dp[i - 1][x - 1], dp[i][j - x]);

                    // Store the minimum number of tests required
                    dp[i][j] = Math.min(dp[i][j], worstCase);
                }
            }
        }

        // Return the minimum tests required for k samples and n temperature levels
        return dp[k][n];
    }

    // Main function to test the implementation
    public static void main(String[] args) {
        int k1 = 1, n1 = 2;
        int k2 = 2, n2 = 6;
        int k3 = 3, n3 = 6;
        //

        // Print the results for given values of k and n
        System.out.println("Minimum tests for k = " + k1 + ", n = " + n1 + ": " + minMeasurements(k1, n1));
        System.out.println("Minimum tests for k = " + k2 + ", n = " + n2 + ": " + minMeasurements(k2, n2));
        System.out.println("Minimum tests for k = " + k3 + ", n = " + n3 + ": " + minMeasurements(k3, n3));
    };
};