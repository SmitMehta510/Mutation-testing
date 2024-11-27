package com.TranquilMind.Algorithms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MergeSortTest {

    @Test
    public void testSort() {
        MergeSort mergeSort = new MergeSort();

        // Test case 1: Random unsorted array
        int[] arr1 = {4, 2, 7, 1, 9};
        int[] expected1 = {1, 2, 4, 7, 9};
        mergeSort.sort(arr1, 0, arr1.length - 1);
        assertArrayEquals(expected1, arr1);

        // Test case 2: Sorted array
        int[] arr2 = {1, 2, 3, 4, 5};
        int[] expected2 = {1, 2, 3, 4, 5};
        mergeSort.sort(arr2, 0, arr2.length - 1);
        assertArrayEquals(expected2, arr2);

        // Test case 3: Reverse sorted array
        int[] arr3 = {5, 4, 3, 2, 1};
        int[] expected3 = {1, 2, 3, 4, 5};
        mergeSort.sort(arr3, 0, arr3.length - 1);
        assertArrayEquals(expected3, arr3);

        // Test case 4: Array with duplicate elements
        int[] arr4 = {3, 2, 1, 2, 3};
        int[] expected4 = {1, 2, 2, 3, 3};
        mergeSort.sort(arr4, 0, arr4.length - 1);
        assertArrayEquals(expected4, arr4);

        // Test case 5: Array with negative numbers
        int[] arr5 = {-4, 2, -7, 1, 9};
        int[] expected5 = {-7, -4, 1, 2, 9};
        mergeSort.sort(arr5, 0, arr5.length - 1);
        assertArrayEquals(expected5, arr5);

        // Test case 6: Empty array
        int[] arr6 = {};
        int[] expected6 = {};
        mergeSort.sort(arr6, 0, arr6.length - 1);
        assertArrayEquals(expected6, arr6);

        // Test case for assertNotEquals
        int[] arr7 = {1, 2, 3};
        int[] invalidExpected = {1, 3, 2}; // Incorrect expected result
        mergeSort.sort(arr7, 0, arr7.length - 1);
        assertNotEquals(invalidExpected, arr7);
    }
}
