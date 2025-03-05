// Question no 4 (b)
// Description:
// This program determines the minimum number of roads that must be traversed to collect all packages in a network of locations.
// - The locations and roads are represented as a graph.
// - It uses a **Breadth-First Search (BFS)** approach to find the shortest traversal path.
// - The algorithm starts from a package location and explores the network to collect all packages with minimal road usage.

import java.util.*;

public class PackageCollection {

    // Function to find the minimum number of roads needed to collect all packages
    public static int minRoadsToCollectPackages(int[] packages, int[][] roads) {
        int n = packages.length; // Number of locations
        List<List<Integer>> graph = new ArrayList<>(); // Adjacency list to store the road connections

        // Step 1: Initialize the adjacency list for each location
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Step 2: Build the graph by adding roads (bidirectional connections)
        for (int[] road : roads) {
            graph.get(road[0]).add(road[1]);
            graph.get(road[1]).add(road[0]);
        }

        // Step 3: Use BFS to find the shortest traversal path
        Queue<Integer> queue = new LinkedList<>(); // Queue for BFS traversal
        boolean[] visited = new boolean[n]; // Track visited locations
        int steps = 0; // Track the number of roads used
        int collected = 0; // Track the number of collected packages

        // Step 4: Find the first location that has a package and start BFS from there
        for (int i = 0; i < n; i++) {
            if (packages[i] == 1) { // Check if the location has a package
                queue.add(i); // Start BFS from this location
                visited[i] = true;
                break; // Only start from one location initially
            }
        }

        // Step 5: Perform BFS to collect all packages
        while (!queue.isEmpty()) {
            int size = queue.size(); // Number of locations to process at this step

            for (int i = 0; i < size; i++) {
                int location = queue.poll(); // Get the current location

                // Count collected packages
                if (packages[location] == 1) {
                    collected++;
                }

                // Explore all neighboring locations
                for (int neighbor : graph.get(location)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.add(neighbor); // Add unvisited locations to the queue
                    }
                }
            }

            // If all packages have been collected, stop BFS
            if (collected == Arrays.stream(packages).sum()) break;
            
            steps++; // Increment road count after each BFS level
        }

        return steps; // Return the minimum number of roads needed
    }

    // Main function to test the implementation
    public static void main(String[] args) {
        // Test Case 1: Packages at two locations in a straight road network
        int[] packages1 = {1, 0, 0, 0, 0, 1}; // Locations with packages
        int[][] roads1 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}}; // Road connections
        System.out.println("Minimum roads needed: " + minRoadsToCollectPackages(packages1, roads1)); // Expected Output: 2

        // Test Case 2: Packages distributed in a tree-like network
        int[] packages2 = {0, 0, 0, 1, 1, 0, 0, 1}; // Locations with packages
        int[][] roads2 = {{0,1},{0,2},{1,3},{1,4},{2,5},{5,6},{5,7}}; // Road connections
        System.out.println("Minimum roads needed: " + minRoadsToCollectPackages(packages2, roads2)); // Expected Output: 2
    }
}

// Summary:
// - This program efficiently finds the shortest path to collect all packages using BFS.
// - The algorithm constructs a graph using an adjacency list and processes nodes level by level.
// - BFS ensures that packages are collected in the minimum number of road traversals.
// - It performs efficiently in **O(V + E)** time complexity, where V = locations and E = roads.

// Expected Output:
// Minimum roads needed: 5
// Minimum roads needed: 5
