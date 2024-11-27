package com.TranquilMind.Algorithms;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BinarySearchTest {

    @Test
    public void testBinarySearch() {
        BinarySearch binarySearch = new BinarySearch();

        // Test case 1
        int[] arr1 = {1, 2, 3, 4, 5};
        int x1 = 3;
        int result1 = binarySearch.binarySearch(arr1, x1);
        int expectedIndex1 = 2;
        assertEquals(expectedIndex1, result1);

        // Test case 2
        int[] arr2 = {10, 20, 30, 40, 50};
        int x2 = 25;
        int result2 = binarySearch.binarySearch(arr2, x2);
        int expectedIndex2 = -1;
        assertEquals(expectedIndex2, result2);

        // Test case for assertNotEquals
        int[] arr3 = {5, 10, 15, 20, 25};
        int x3 = 15;
        int result3 = binarySearch.binarySearch(arr3, x3);
        int invalidExpectedIndex = 0; // Incorrect expected result
        assertNotEquals(invalidExpectedIndex, result3);
    }
}
