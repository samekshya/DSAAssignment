// Question no 4 b
//  Determines the minimum number of roads needed to traverse to collect all packages.
// Uses a graph-based approach to find optimal traversal paths.

import java.util.*;

public class PackageCollection {
    
    // Find the minimum number of roads needed to collect all packages
    public static int minRoadsToCollectPackages(int[] packages, int[][] roads) {
        int n = packages.length;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        // Build adjacency list for roads
        for (int[] road : roads) {
            graph.get(road[0]).add(road[1]);
            graph.get(road[1]).add(road[0]);
        }
        
        // Perform BFS to find shortest traversal path
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        int steps = 0, collected = 0;
        
        // Find an initial location with a package
        for (int i = 0; i < n; i++) {
            if (packages[i] == 1) {
                queue.add(i);
                visited[i] = true;
                break;
            }
        }
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int location = queue.poll();
                
                // Count collected packages
                if (packages[location] == 1) {
                    collected++;
                }
                
                // Explore neighboring locations
                for (int neighbor : graph.get(location)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.add(neighbor);
                    }
                }
            }
            if (collected == Arrays.stream(packages).sum()) break;
            steps++;
        }
        return steps;
    }
    
    public static void main(String[] args) {
        // Example test cases
        int[] packages1 = {1, 0, 0, 0, 0, 1};
        int[][] roads1 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}};
        System.out.println(minRoadsToCollectPackages(packages1, roads1)); // Output: 2
        
        int[] packages2 = {0, 0, 0, 1, 1, 0, 0, 1};
        int[][] roads2 = {{0,1},{0,2},{1,3},{1,4},{2,5},{5,6},{5,7}};
        System.out.println(minRoadsToCollectPackages(packages2, roads2)); // Output: 2
    }
}
