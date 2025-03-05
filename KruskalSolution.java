// Question no 3 (a)
// Description: Implements Kruskal's Algorithm using Union-Find to find the Minimum Spanning Tree (MST).
// It connects network devices at the lowest possible cost by considering both module installation and direct connections.

import java.util.*;

// Union-Find data structure for efficient cycle detection in Kruskal's Algorithm
class UnionFind {
    private int[] parent, rank;

    // Constructor to initialize the Union-Find structure
    public UnionFind(int n) {
        parent = new int[n]; // Stores the parent of each node
        rank = new int[n];   // Stores the rank of each set for optimization
        for (int i = 0; i < n; i++) {
            parent[i] = i; // Each node is its own parent initially (self-loop)
        }
    }

    // Find function with path compression to make future searches faster
    public int find(int u) {
        if (parent[u] != u) {
            parent[u] = find(parent[u]); // Recursively find the root and compress path
        }
        return parent[u];
    }

    // Union function with rank optimization to merge sets efficiently
    public boolean union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);
        
        // If both nodes are already in the same set, do nothing
        if (rootU == rootV) return false;

        // Attach the smaller tree under the larger tree to keep the structure balanced
        if (rank[rootU] > rank[rootV]) {
            parent[rootV] = rootU;
        } else if (rank[rootU] < rank[rootV]) {
            parent[rootU] = rootV;
        } else {
            parent[rootV] = rootU;
            rank[rootU]++; // Increase the rank when merging two equal-sized trees
        }
        return true;
    }
}

public class KruskalSolution {
    // Function to calculate the minimum cost to connect all devices
    public static int minCostToConnectDevices(int n, int[] modules, int[][] connections) {
        List<int[]> edges = new ArrayList<>(); // List to store all edges (module costs and direct connections)

        // Step 1: Add virtual edges connecting each device to a virtual node (n)
        // These represent the cost of installing a communication module for each device
        for (int i = 0; i < n; i++) {
            edges.add(new int[]{modules[i], i, n}); // Virtual node at index 'n'
        }

        // Step 2: Add direct connections between devices
        // Each connection has a cost and connects two devices
        for (int[] conn : connections) {
            edges.add(new int[]{conn[2], conn[0] - 1, conn[1] - 1}); // Convert to 0-based index
        }

        // Step 3: Sort edges by cost in ascending order (Greedy approach for Kruskal's Algorithm)
        edges.sort(Comparator.comparingInt(a -> a[0]));

        // Step 4: Use Union-Find to construct the Minimum Spanning Tree (MST)
        UnionFind uf = new UnionFind(n + 1); // 'n' devices + 1 virtual node
        int totalCost = 0; // Stores the final minimum cost
        int edgesUsed = 0; // Count of edges included in the MST

        // Step 5: Process edges in increasing order of cost
        for (int[] edge : edges) {
            int cost = edge[0]; // Cost of this edge
            int u = edge[1];    // First device (or virtual node)
            int v = edge[2];    // Second device (or virtual node)

            // If adding this edge does not form a cycle, include it in the MST
            if (uf.union(u, v)) {
                totalCost += cost; // Add edge cost to total cost
                edgesUsed++; // Increase count of used edges
                
                // Stop early if we have connected all 'n' devices
                if (edgesUsed == n) break;
            }
        }

        return totalCost; // Return the minimum total cost to connect all devices
    }

    // Main function to test the implementation
    public static void main(String[] args) {
        // Number of devices
        int n = 3;
        
        // Cost of installing a communication module for each device
        int[] modules = {1, 2, 2}; 
        
        // Direct connection costs between devices (device1, device2, cost)
        int[][] connections = {{1, 2, 1}, {2, 3, 1}}; 

        // Print the minimum cost required to connect all devices
        System.out.println("Minimum cost to connect all devices: " + minCostToConnectDevices(n, modules, connections)); // Expected output: 3
    }
}

// Summary: 
// This program uses Kruskal’s Algorithm and the Union-Find data structure to find the Minimum Spanning Tree (MST).
// The algorithm sorts all possible connections (module installation and direct links) by cost and adds them to the network greedily.
// Union-Find ensures that no cycles are formed while connecting the devices.
// The approach guarantees the most cost-effective way to connect all devices into a single network.

// Results:
// Minimum cost to connect all devices: 3
