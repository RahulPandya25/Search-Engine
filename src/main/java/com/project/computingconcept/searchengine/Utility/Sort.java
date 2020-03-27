package com.project.computingconcept.searchengine.Utility;

import com.project.computingconcept.searchengine.Model.Result;

public class Sort {

    private static final int CUTOFF = 3;

    /**
     * Method to swap to elements in an array.
     *
     * @param a      an array of objects.
     * @param index1 the index of the first object.
     * @param index2 the index of the second object.
     */
    public static <AnyType> void swapReferences(AnyType[] a, int index1, int index2) {
        AnyType tmp = a[index1];
        a[index1] = a[index2];
        a[index2] = tmp;
    }

    /**
     * Return median of left, center, and right.
     * Order these and hide the pivot.
     *
     * @return
     */
    private static <AnyType extends Comparable<? super AnyType>>
    Result median3(Result[] a, int left, int right, String word) {
        int center = (left + right) / 2;
        if (a[center].getTrieST().getWordCount(word) < a[left].getTrieST().getWordCount(word))
            swapReferences(a, left, center);
        if (a[right].getTrieST().getWordCount(word) < a[left].getTrieST().getWordCount(word))
            swapReferences(a, left, right);
        if (a[right].getTrieST().getWordCount(word) < a[center].getTrieST().getWordCount(word))
            swapReferences(a, center, right);

        // Place pivot at position right - 1
        swapReferences(a, center, right - 1);
        return a[right - 1];
    }

    /**
     * Internal insertion sort routine for subarrays
     * that is used by quicksort.
     *
     * @param results an array of Comparable items.
     * @param left    the left-most index of the subarray.
     * @param right   the right-most index of the subarray.
     */
    private static <AnyType extends Comparable<? super AnyType>>
    void insertionSort(Result[] results, int left, int right, String word) {
        for (int p = left + 1; p <= right; p++) {
            Result tmp = results[p];
            int j;

            for (j = p; j > left && tmp.getTrieST().getWordCount(word) < results[j - 1].getTrieST().getWordCount(word); j--)
                results[j] = results[j - 1];
            results[j] = tmp;
        }
    }

    /**
     * Simple insertion sort.
     *
     * @param results an array of Comparable items.
     */
    public static <AnyType extends Comparable<? super AnyType>>
    void insertionSort(Result[] results, String word) {
        int j;

        for (int p = 1; p < results.length; p++) {
            Result tmp = results[p];
            for (j = p; j > 0 && tmp.getTrieST().getWordCount(word) < results[j - 1].getTrieST().getWordCount(word); j--)
                results[j] = results[j - 1];
            results[j] = tmp;
        }
    }

    /**
     * Quick selection algorithm.
     * Places the kth smallest item in a[k-1].
     *
     * @param a an array of Comparable items.
     * @param k the desired rank (1 is minimum) in the entire array.
     */
    public static <AnyType extends Comparable<? super AnyType>>
    void quickSelect(Result[] a, int k, String word) {
        quickSelect(a, 0, a.length - 1, k, word);
    }

    /**
     * Internal selection method that makes recursive calls.
     * Uses median-of-three partitioning and a cutoff of 10.
     * Places the kth smallest item in a[k-1].
     *
     * @param a     an array of Comparable items.
     * @param left  the left-most index of the subarray.
     * @param right the right-most index of the subarray.
     * @param k     the desired index (1 is minimum) in the entire array.
     */
    private static <AnyType extends Comparable<? super AnyType>>
    void quickSelect(Result[] a, int left, int right, int k, String word) {
        if (left + CUTOFF <= right) {
            Result pivot = median3(a, left, right, word);

            // Begin partitioning
            int i = left, j = right - 1;
            for (; ; ) {
                while (a[++i].getTrieST().getWordCount(word) - pivot.getTrieST().getWordCount(word) < 0) {
                }
                while (a[--j].getTrieST().getWordCount(word) - pivot.getTrieST().getWordCount(word) > 0) {
                }
                if (i < j)
                    swapReferences(a, i, j);
                else
                    break;
            }

            swapReferences(a, i, right - 1);   // Restore pivot

            if (k <= i)
                quickSelect(a, left, i - 1, k, word);
            else if (k > i + 1)
                quickSelect(a, i + 1, right, k, word);
        } else  // Do an insertion sort on the subarray
            insertionSort(a, left, right, word);
    }

    /**
     * Quicksort algorithm.
     *
     * @param results an array of Comparable items.
     */
    public static <AnyType extends Comparable<? super AnyType>>
    void quicksort(Result[] results, String word) {
        quicksort(results, 0, results.length - 1, word);
    }

    /**
     * Internal quicksort method that makes recursive calls.
     * Uses median-of-three partitioning and a cutoff of 10.
     *
     * @param results an array of Comparable items.
     * @param left    the left-most index of the subarray.
     * @param right   the right-most index of the subarray.
     */
    private static <AnyType extends Comparable<? super AnyType>>
    void quicksort(Result[] results, int left, int right, String word) {
        if (left + CUTOFF <= right) {
            Result pivot = median3(results, left, right, word);

            // Begin partitioning
            int i = left, j = right - 1;
            for (; ; ) {
                while (results[++i].getTrieST().getWordCount(word) - pivot.getTrieST().getWordCount(word) < 0) {
                }
                while (results[--j].getTrieST().getWordCount(word) - pivot.getTrieST().getWordCount(word) > 0) {
                }
                if (i < j)
                    swapReferences(results, i, j);
                else
                    break;
            }

            swapReferences(results, i, right - 1);   // Restore pivot

            quicksort(results, left, i - 1, word);    // Sort small elements
            quicksort(results, i + 1, right, word);   // Sort large elements
        } else  // Do an insertion sort on the subarray
            insertionSort(results, left, right, word);
    }

}
