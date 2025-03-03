//Question no 3 (a)
// Description: Implements Kruskal's Algorithm using Union-Find to find the Minimum Spanning Tree (MST).
// Connects network devices at the minimum possible cost by considering module installation and direct connections.

import java.util.*;

// Union-Find data structure for efficient cycle detection in Kruskal's Algorithm
class UnionFind {
    private int[] parent, rank;

    // Constructor to initialize Union-Find structure
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i; // Each node is its own parent initially
        }
    }

    // Find function with path compression for efficiency
    public int find(int u) {
        if (parent[u] != u) {
            parent[u] = find(parent[u]); // Path compression
        }
        return parent[u];
    }

    // Union function with rank optimization to merge sets
    public boolean union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);
        if (rootU == rootV) return false; // Already connected

        if (rank[rootU] > rank[rootV]) {
            parent[rootV] = rootU;
        } else if (rank[rootU] < rank[rootV]) {
            parent[rootU] = rootV;
        } else {
            parent[rootV] = rootU;
            rank[rootU]++;
        }
        return true;
    }
}

public class KruskalSolution {
    // Function to calculate the minimum cost to connect all devices
    public static int minCostToConnectDevices(int n, int[] modules, int[][] connections) {
        List<int[]> edges = new ArrayList<>(); // List to store all edges (module costs and direct connections)

        // Step 1: Add virtual edges connecting each device to a virtual node (n)
        for (int i = 0; i < n; i++) {
            edges.add(new int[]{modules[i], i, n}); // Virtual node at index n
        }

        // Step 2: Add direct connections between devices
        for (int[] conn : connections) {
            edges.add(new int[]{conn[2], conn[0] - 1, conn[1] - 1}); // Convert to 0-based index
        }

        // Step 3: Sort edges by cost (Greedy approach of Kruskal's Algorithm)
        edges.sort(Comparator.comparingInt(a -> a[0]));

        // Step 4: Use Union-Find to construct the Minimum Spanning Tree (MST)
        UnionFind uf = new UnionFind(n + 1); // n devices + virtual node
        int totalCost = 0, edgesUsed = 0;

        for (int[] edge : edges) {
            int cost = edge[0], u = edge[1], v = edge[2];
            if (uf.union(u, v)) { // If successfully connected
                totalCost += cost;
                edgesUsed++;
                if (edgesUsed == n) break; // Stop when all nodes are connected
            }
        }

        return totalCost; // Return the minimum total cost to connect all devices
    }

    // Main function to test the implementation
    public static void main(String[] args) {
        int n = 3;
        int[] modules = {1, 2, 2}; // Cost of installing communication modules
        int[][] connections = {{1, 2, 1}, {2, 3, 1}}; // Direct connection costs between devices

        // Print the minimum cost required to connect all devices
        System.out.println(minCostToConnectDevices(n, modules, connections)); // Output: 3
    }
}
