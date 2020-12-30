package com.pltech.study.java;

/**
 * Created by Pang Li on 2020/12/28
 */
public class HeapStudy {
    /**
     * 按升序排序数组
     *
     * @param arr array
     */
    public void sortArrayUpwards(int[] arr) {
        int length = arr.length;
        //构建大顶堆
        for (int i = (length - 2) >> 1; i >= 0; i--) {
            // 从第一个非叶子节点从下往上、从右至左调整堆结构
            adjustHeap(arr, i, length);
        }
        for (int i = length - 1; i > 0; i--) {
            // 将堆顶元素与末尾元素进行交换
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // 重新调整堆
            adjustHeap(arr, 0, i);
        }
    }

    /**
     * 求数组中k个最小的数
     *
     * @param arr 原始数组
     * @param k   k
     */
    public int[] smallestKnums(int[] arr, int k) {
        int length = arr.length;
        if (length <= k) return arr;

        int[] kMinArr = new int[k];
        for (int i = (length - 2) >> 1; i >= 0; i--) {
            adjustHeap(arr, i, length);
        }
        for (int i = length - 1; i > 0; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;

            adjustHeap(arr, 0, i);
        }
        if (k >= 0) System.arraycopy(arr, 0, kMinArr, 0, k);

        return kMinArr;
    }

    private void adjustHeap(int[] arr, int parent, int length) {
        int temp = arr[parent]; // 现保存当前堆顶的元素
        int child = 2 * parent + 1; // 从堆顶节点的左子节点开始
        while (child < length) {
            if (child + 1 < length && arr[child] < arr[child + 1]) {
                child++;
            }
            if (temp >= arr[child]) {
                break;
            }
            // 子节点的值大于了父节点，将子节点放到父节点位置
            arr[parent] = arr[child];
            // 更新父节点子节点下标，继续调整
            parent = child;
            child = 2 * parent + 1;
        }
        arr[parent] = temp; // 将原来的堆顶元素值放到它最终的位置
    }
}
