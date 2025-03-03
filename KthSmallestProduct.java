//Question no 1 (b)
// Description: Finds the k-th smallest product from two sorted arrays.
// Uses binary search to efficiently count valid pairs and locate the required product.

import java.util.Arrays;

public class KthSmallestProduct {

    // Function to count how many pairs have a product <= mid
    private static int countPairs(int[] arr1, int[] arr2, long mid) {
        int count = 0;
        int n = arr2.length;
        
        for (int num : arr1) { // Iterate through the first array
            if (num >= 0) {  // If the number is positive or zero
                int low = 0, high = n;
                while (low < high) { // Binary search to count valid pairs
                    int midIndex = low + (high - low) / 2;
                    if ((long) num * arr2[midIndex] <= mid) {
                        low = midIndex + 1; // Move right if product is within limit
                    } else {
                        high = midIndex; // Move left otherwise
                    }
                }
                count += low; // Add count of valid pairs
            } else {  // If the number is negative
                int low = 0, high = n;
                while (low < high) { // Binary search again
                    int midIndex = low + (high - low) / 2;
                    if ((long) num * arr2[midIndex] > mid) {
                        low = midIndex + 1; // Move right if product is too high
                    } else {
                        high = midIndex; // Move left otherwise
                    }
                }
                count += (n - low); // Add remaining elements count
            }
        }
        return count; // Return the number of valid pairs
    }

    // Main function to find the k-th smallest product
    public static long kthSmallestProduct(int[] returns1, int[] returns2, int k) {
        Arrays.sort(returns1);  // Sort first array
        Arrays.sort(returns2);  // Sort second array
        
        long left = (long) returns1[0] * returns2[0];  // Smallest possible product
        long right = (long) returns1[returns1.length - 1] * returns2[returns2.length - 1]; // Largest possible product
        
        while (left < right) { // Binary search to find the kth smallest product
            long mid = left + (right - left) / 2; // Mid-point value
            int count = countPairs(returns1, returns2, mid); // Count valid pairs
            
            if (count < k) {
                left = mid + 1;  // Increase range if not enough valid pairs
            } else {
                right = mid;  // Decrease range otherwise
            }
        }
        return left; // The k-th smallest product
    }

    // Main function to test the implementation
    public static void main(String[] args) {
        int[] returns1 = {-4, -2, 0, 3}; // Example input array 1
        int[] returns2 = {2, 4}; // Example input array 2
        int k = 6; // Find the 6th smallest product
        
        // Print the result
        System.out.println("The " + k + "th smallest product is: " + kthSmallestProduct(returns1, returns2, k));
    }
}
