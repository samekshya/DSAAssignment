
public class ClosestPair2D {
    
    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length;
        int minDistance = Integer.MAX_VALUE; // Store the minimum distance found
        int[] result = {-1, -1}; // Store the indices of the closest pair
        
        // Loop through every pair of points
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Calculate the Manhattan distance
                int distance = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);
                
                // If we find a smaller distance, update result
                if (distance < minDistance || (distance == minDistance && (i < result[0] || (i == result[0] && j < result[1])))) {
                    minDistance = distance;
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        int[] x_coords = {1, 2, 3, 2, 4};
        int[] y_coords = {2, 3, 1, 2, 3};
        int[] closestPair = findClosestPair(x_coords, y_coords);
        System.out.println("Closest pair of points: [" + closestPair[0] + ", " + closestPair[1] + "]"); // Output: [0, 3]
    }
}
