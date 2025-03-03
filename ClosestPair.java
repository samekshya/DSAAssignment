//Question no 2(b)
import java.util.Arrays;

public class ClosestPair {
    
    // Function to find the lexicographically smallest pair with the smallest Manhattan distance
    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length; // Number of points
        int minDistance = Integer.MAX_VALUE; // Store the smallest distance found
        int[] result = new int[2]; // Store the indices of the closest pair

        // Iterate through all pairs of points (i, j) where i < j
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                
                // Calculate Manhattan distance
                int distance = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);

                // If we find a smaller distance, update the result
                if (distance < minDistance) {
                    minDistance = distance;
                    result[0] = i;
                    result[1] = j;
                }
                // If we find the same distance, pick the lexicographically smaller pair
                else if (distance == minDistance) {
                    if (i < result[0] || (i == result[0] && j < result[1])) {
                        result[0] = i;
                        result[1] = j;
                    }
                }
            }
        }

        return result; // Return the indices of the closest pair
    }

    public static void main(String[] args) {
        // Example input
        int[] x_coords = {1, 2, 3, 2, 4};
        int[] y_coords = {2, 3, 1, 2, 3};

        // Call the function and get the result
        int[] closestPair = findClosestPair(x_coords, y_coords);

        // Print the result
        System.out.println("Closest Pair: " + Arrays.toString(closestPair));
    }
}
