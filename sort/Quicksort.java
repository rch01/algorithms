package algorithms.sort;

import java.util.Arrays;

/**
 * Quicksort is a divide and conquer algorithm:
 * (i) First divides a large array into two smaller sub-arrays: the low elements and the high elements.
 * (ii) Then recursively sorts the sub-arrays.
 *
 * Three parts to the algorithm: (a) Recursion (b) Partition scheme (c) Pivot value
 *
 * Choices made in (b) and (c) affect the overall efficiency and performance of the algorithm.
 */

public class Quicksort {
    private static int swapCount = 0;

    private void printPartition(String s, int[] a, int lo, int hi) {
        int [] x = new int [(hi - lo) + 1];

        for (int i = lo, n = 0; i <= hi; i++, n++) {
            x[n] = a[i];
        }

        System.out.format("%s%s\n", s, Arrays.toString(x));
    }

    private void swap(int[] arr, int i1, int i2) {
        final int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
        swapCount++;
    }

    private int median(int[] a, int lo, int hi) {
        final int m = (lo + hi) / 2; // this could result in an integer overflow - https://research.googleblog.com/2006/06/extra-extra-read-all-about-it-nearly.html

        if (a[lo] > a[m]) swap(a, lo, m);
        if (a[lo] > a[hi]) swap(a, lo, hi);
        if (a[m] > a[hi]) swap(a, m, hi);

        //swap(a, m, hi - 1);
        return m;
    }

    /**
     * Lomuto partition scheme
     * @param a array to be sorted
     * @param lo starting index
     * @param hi ending index
     * @return index of pivot, which is now in its final place within array
     */
    private int partitionLomuto(int[] a, int lo, int hi) {
        final int pivotIndex = median(a, lo, hi);
        final int pivot = a[pivotIndex];
        int i = lo - 1;

        swap(a, pivotIndex, hi); // pivot moved to end of array
        for (int j = lo; j < hi; j++) {
            if (a[j] <= pivot) {
                swap(a, ++i, j);
            }
        }
        swap(a, ++i, hi); // pivot moved back to rightful place

        return i;
    }

    private int partitionHoare(int[] a, int lo, int hi) {
        final int pivotIndex = median(a, lo, hi);
        final int pivot = a[pivotIndex];
        int i = lo - 1;
        int j = hi + 1;

        while (true) {
            do i++;
            while (a[i] < pivot);

            do j--;
            while (a[j] > pivot);

            if (i < j)
                swap(a, i, j);
            else
                return j;
        }
    }

    private void qsort(int[] a, int start, int end) {
        if (start >= end) return; // base case: if start and end index cross, then the array has been sorted and can be returned.

        final int i = partitionHoare(a, start, end);
        qsort(a, start, i);
        qsort(a, i + 1, end);
    }

    /**
     * API for this class.
     * @param a array of integers
     * @return sorted array of integers
     */
    public int[] sort(int[] a) {
        qsort(a, 0, a.length - 1);
        return a;
    }

    public static void main(String [] qs) {
        int [] e1 = {9, 6, 5, 0, 8, 2, 4, 7};
        int [] e2 = {4, 3, 2, 1};
        int [] e3 = {1, 2, 3, 4};
        int [] e4 = {3, 1, 4, 5, 9, 1, 2, 6, 8, 5, 7};
        int [] e5 = {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
        int [] e6 = {};

        System.out.println("\nUnsorted: " + Arrays.toString(e4) +
                            "\nSorted: " + Arrays.toString(new Quicksort().sort(e4)) +
                            "\nSwaps: " + swapCount);
    }
}
