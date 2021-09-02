package com.pltech.study.java.sort;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        int[] arr = new int[]{3, 1, 9, 4, 6, 2, 0, 5, 8, 7};
        System.out.println("after quickSort is: " + Arrays.toString(quickSort.quickSort(arr, 0, arr.length - 1)));
    }

    public int[] quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int part = partition(arr, left, right);
            quickSort(arr, left, part - 1);
            quickSort(arr, part + 1, right);
        }
        return arr;
    }

    private int partition(int[] arr, int left, int right) {
        // 设定基准值（pivot）
        int index = left + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] < arr[left]) {
                swap(arr, i, index);
                index++;
            }
        }
        swap(arr, left, index - 1);
        return index - 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private int partition1(int[] arr, int left, int right) {
        int pivot = arr[left];
        while (left < right) {
            while (left < right && arr[right] > pivot) {
                --right;
            }
            arr[left] = arr[right];
            while (left < right && arr[left] < pivot) {
                ++left;
            }
            arr[right] = arr[left];
        }
        arr[left] = pivot;
        return left;
    }
}
