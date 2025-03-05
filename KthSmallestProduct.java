// Question no 1 (b)
// Description: This program finds the k-th smallest product from two sorted arrays.
// It uses binary search to efficiently count valid pairs and locate the required product.

import java.util.Arrays;

public class KthSmallestProduct {

    // Function to count how many pairs have a product <= mid
    private static int countPairs(int[] arr1, int[] arr2, long mid) {
        int count = 0; // Store the number of valid pairs
        int n = arr2.length; // Length of the second array

        // Loop through each number in the first array
        for (int num : arr1) {  
            if (num >= 0) {  // If the number is positive or zero
                int low = 0, high = n;
                // Perform binary search to find valid pairs
                while (low < high) {
                    int midIndex = low + (high - low) / 2;
                    if ((long) num * arr2[midIndex] <= mid) {
                        low = midIndex + 1; // Move right if product is within limit
                    } else {
                        high = midIndex; // Move left otherwise
                    }
                }
                count += low; // Add count of valid pairs found
            } else {  // If the number is negative
                int low = 0, high = n;
                // Perform binary search again
                while (low < high) {
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

    // Function to find the k-th smallest product
    public static long kthSmallestProduct(int[] returns1, int[] returns2, int k) {
        Arrays.sort(returns1);  // Sort the first array in ascending order
        Arrays.sort(returns2);  // Sort the second array in ascending order

        // Set the range for binary search
        long left = (long) returns1[0] * returns2[0];  // Smallest possible product
        long right = (long) returns1[returns1.length - 1] * returns2[returns2.length - 1]; // Largest possible product

        // Binary search to find the k-th smallest product
        while (left < right) {
            long mid = left + (right - left) / 2; // Mid-point value
            int count = countPairs(returns1, returns2, mid); // Count valid pairs that have a product <= mid

            if (count < k) {
                left = mid + 1;  // If there are not enough valid pairs, move to higher range
            } else {
                right = mid;  // Otherwise, reduce the range to find the exact k-th smallest product
            }
        }
        return left; // Return the k-th smallest product
    }

    // Main function to test the implementation
    public static void main(String[] args) {
        int[] returns1 = {-4, -2, 0, 3}; // First sorted array
        int[] returns2 = {2, 4}; // Second sorted array
        int k = 6; // Find the 6th smallest product
        
        // Print the result
        System.out.println("The " + k + "th smallest product is: " + kthSmallestProduct(returns1, returns2, k));
    }
}

// Summary:
// This program efficiently finds the k-th smallest product from two sorted arrays using binary search.
// Instead of generating all possible products (which is inefficient), it counts valid products efficiently.
// The binary search reduces the problem size, making it much faster than a brute-force approach.
// It correctly handles both positive and negative numbers using two separate binary searches.

// Results:
// The 6th smallest product is: 0
