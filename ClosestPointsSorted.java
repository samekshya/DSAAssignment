//Question no 2(b)
import java.util.*;

public class ClosestPointsSorted {

    // Function to find the closest pair of points
    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length;
        
        // Create an array of points as (x, y, index) to keep track of original indices
        int[][] points = new int[n][3];
        for (int i = 0; i < n; i++) {
            points[i][0] = x_coords[i]; // Store x-coordinate
            points[i][1] = y_coords[i]; // Store y-coordinate
            points[i][2] = i; // Store original index
        }

        // Sort points based on x-coordinates first, then y-coordinates if x is the same
        Arrays.sort(points, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));

        int minDistance = Integer.MAX_VALUE; // Smallest distance found
        int[] result = new int[2]; // Array to store the closest indices

        // Compare only adjacent points in the sorted order
        for (int i = 0; i < n - 1; i++) {
            int distance = Math.abs(points[i][0] - points[i + 1][0]) + Math.abs(points[i][1] - points[i + 1][1]);

            // Update the closest pair if a smaller distance is found
            if (distance < minDistance) {
                minDistance = distance;
                result[0] = Math.min(points[i][2], points[i + 1][2]); // Store the smaller index first
                result[1] = Math.max(points[i][2], points[i + 1][2]); // Store the larger index second
            }
            // If distances are the same, ensure lexicographical order
            else if (distance == minDistance) {
                if (Math.min(points[i][2], points[i + 1][2]) < result[0] ||
                   (Math.min(points[i][2], points[i + 1][2]) == result[0] && Math.max(points[i][2], points[i + 1][2]) < result[1])) {
                    result[0] = Math.min(points[i][2], points[i + 1][2]);
                    result[1] = Math.max(points[i][2], points[i + 1][2]);
                }
            }
        }

        return result; // Return the best closest pair of indices
    }

    public static void main(String[] args) {
        // Test Case 1
        int[] x_coords1 = {1, 2, 3, 2, 4};
        int[] y_coords1 = {2, 3, 1, 2, 3};
        int[] closest1 = findClosestPair(x_coords1, y_coords1);
        System.out.println("Closest pair of indices: " + Arrays.toString(closest1)); // Output: [0, 3]

        // Test Case 2
        int[] x_coords2 = {0, 1, 2, 3};
        int[] y_coords2 = {0, 2, 4, 6};
        int[] closest2 = findClosestPair(x_coords2, y_coords2);
        System.out.println("Closest pair of indices: " + Arrays.toString(closest2)); // Output: [0, 1]
    }
}
