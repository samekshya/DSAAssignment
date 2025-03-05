// Question no 2 (b)
// Description: This program finds the closest pair of points based on Manhattan distance. 
// It sorts the points first to reduce unnecessary comparisons and ensures the lexicographically smallest pair is selected.

import java.util.*;

public class ClosestPointsSorted {

    // Function to find the closest pair of points
    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length; // Number of points
        
        // Create an array to store points along with their original indices
        int[][] points = new int[n][3]; // Each point stores (x, y, index)
        
        // Storing coordinates along with their original index
        for (int i = 0; i < n; i++) {
            points[i][0] = x_coords[i]; // Store x-coordinate
            points[i][1] = y_coords[i]; // Store y-coordinate
            points[i][2] = i; // Store the original index (to maintain order)
        }

        // Sorting the points: first by x-coordinate, then by y-coordinate if x is the same
        Arrays.sort(points, (a, b) -> 
            a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1])
        );

        int minDistance = Integer.MAX_VALUE; // Store the minimum Manhattan distance found
        int[] result = new int[2]; // Stores the indices of the closest pair

        // Compare each point with its next neighbor in the sorted order
        for (int i = 0; i < n - 1; i++) {
            // Calculate the Manhattan distance between two adjacent points
            int distance = Math.abs(points[i][0] - points[i + 1][0]) + Math.abs(points[i][1] - points[i + 1][1]);

            // If we find a smaller distance, update the closest pair
            if (distance < minDistance) {
                minDistance = distance;
                result[0] = Math.min(points[i][2], points[i + 1][2]); // Store the smaller index first
                result[1] = Math.max(points[i][2], points[i + 1][2]); // Store the larger index second
            }
            // If the distance is the same, choose the lexicographically smaller pair
            else if (distance == minDistance) {
                if (Math.min(points[i][2], points[i + 1][2]) < result[0] ||
                   (Math.min(points[i][2], points[i + 1][2]) == result[0] && Math.max(points[i][2], points[i + 1][2]) < result[1])) {
                    result[0] = Math.min(points[i][2], points[i + 1][2]);
                    result[1] = Math.max(points[i][2], points[i + 1][2]);
                }
            }
        }

        return result; // Return the closest pair of indices
    }

    // Main function to test the implementation
    public static void main(String[] args) {
        // Test Case 1: A set of points
        int[] x_coords1 = {1, 2, 3, 2, 4}; // X-coordinates
        int[] y_coords1 = {2, 3, 1, 2, 3}; // Y-coordinates
        int[] closest1 = findClosestPair(x_coords1, y_coords1); // Find closest pair
        System.out.println("Closest pair of indices: " + Arrays.toString(closest1)); // Output should be [0, 3]

        // Test Case 2: Another set of points
        int[] x_coords2 = {0, 1, 2, 3}; // X-coordinates
        int[] y_coords2 = {0, 2, 4, 6}; // Y-coordinates
        int[] closest2 = findClosestPair(x_coords2, y_coords2); // Find closest pair
        System.out.println("Closest pair of indices: " + Arrays.toString(closest2)); // Output should be [0, 1]
    }
}

// Summary: 
// This program efficiently finds the closest pair of points using Manhattan distance. 
// It first sorts the points, which makes it easier to find the closest pair by only comparing adjacent points.
// Sorting reduces unnecessary comparisons and improves performance.
// If multiple pairs have the same distance, the lexicographically smaller pair is chosen.
//Results:
// Closest pair of indices: [0, 3]
// Closest pair of indices: [0, 1]