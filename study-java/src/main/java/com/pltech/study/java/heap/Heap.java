package com.pltech.study.java.heap;

/**
 * Created by Pang Li on 2021/3/14
 */
public class Heap {
    private final int[] arr;
    private int n = 0;

    public Heap(int[] array) {
        arr = array;
    }

    /**
     * 节点下沉
     *
     * @param i 要下沉的节点位置
     */
    public void sink(int i) {
        int j = 0; //记录左右子节点位置
        int t = arr[i]; //记录要下沉的元素
        while ((j = (i << 1) + 1) < n) {
            if (j + 1 < n && arr[j] < arr[j + 1]) {
                j++;
            }

            if (arr[j] <= t) {
                break;
            } else {
                arr[i] = arr[j];
                i = j;
            }
        }
        //把t放到找到的位置
        arr[i] = t;
    }

    /**
     * 节点上浮
     */
    public void swim(int i) {
        int t = arr[i];
        int parent = 0;
        //如果存在父节点
        while (i > 0 && (parent = (i - 1) >> 1) != i) {
            //父节点的值比t小
            if (arr[i] < t) {
                arr[i] = arr[parent];
                i = parent;
            } else {
                break;
            }
        }
        arr[i] = t;
    }

    /**
     * 追加元素到堆的尾部
     *
     * @param v 待追加的元素
     */
    public void push(int v) {
        //先把元素追加到数组末尾，然后进行上浮操作
        arr[++n] = v;
        swim(n - 1);
    }

    /**
     * 取出堆顶元素
     */
    public int pop() {
        int a = arr[0];
        arr[0] = arr[n - 1];
        sink(0);
        return a;
    }

    public int size() {
        return n;
    }
}
