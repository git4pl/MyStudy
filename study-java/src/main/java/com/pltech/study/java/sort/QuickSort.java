package com.pltech.study.java.sort;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class QuickSort {
    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        int[] arr = new int[]{3, 1, 9, 4, 6, 2, 0, 5, 8, 7};
        System.out.println("after quickSort is: " + Arrays.toString(quickSort.quickSort_(arr, 0, arr.length - 1)));
    }

    /**
     * 递归方式实现快速排序
     *
     * @param arr   待排序数组
     * @param left  数组最左边下标
     * @param right 数组最右边下标
     * @return 排序后的数组
     */
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

    /**
     * 非递归实现快速排序
     *
     * @param arr   待排序数组
     * @param left  数组最左边下标
     * @param right 数组最右边下标
     * @return 排序后的数组
     */
    public int[] quickSort_(int[] arr, int left, int right) {
        if (arr == null || left < 0 || right < 0 || right < left) {
            return arr;
        }
        int low, high;
        Deque<Integer> stack = new LinkedList<>();
        stack.push(right);  //先存最右边的下标
        stack.push(left);   //再存最左边的下标
        while (!stack.isEmpty()) {
            low = stack.pop();
            high = stack.pop();
            if (low < high) {
                int k = partition1(arr, low, high);
                if (k > low) {
                    stack.push(k - 1);
                    stack.push(low);
                }
                if (k < high) {
                    stack.push(high);
                    stack.push(k + 1);
                }
            }
        }
        return arr;
    }
}
