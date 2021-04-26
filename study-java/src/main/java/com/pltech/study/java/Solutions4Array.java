package com.pltech.study.java;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 数组操作相关的LeetCode题目
 * Created by Pang Li on 2021/2/27
 */
public class Solutions4Array {

    public static void main(String[] args) {
        int[] heights = {0,2, 1, 5, 6, 4, 3, 7};
        //int[] heights = {1, 1};
        Solutions4Array solution = new Solutions4Array();
        int[] res = solution.smallestK_(heights, 4);
        System.out.println(Arrays.toString(res));
    }

    //*******************************************************************************************//

    /**
     * 给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
     * 初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。你可以假设 nums1 的空间大小等于 m + n，这样它就有足够的空间保存来自 nums2 的元素。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/merge-sorted-array
     *
     * 解法分析：
     * 解法一：合并后再排序，最直观的方法是先将数组 nums2 放进数组 nums1 的尾部，然后直接对整个数组进行排序。
     *      时间复杂度为O((m+n)log(m+n)), 空间复杂度为O(1)
     * 解法二：双指针，借助一个长度为m的辅助数组，将nums1里的数据先拷贝到辅助数组里，再将两个有序数组的数据merge到数组nums1里
     *      时间复杂度为O(m+n), 空间复杂度为O(m)
     * 解法三：双指针，从数组最后一个元素往前遍历，算法如下
     *      时间复杂度为O(m+n), 空间复杂度为O(1)
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p = (m--) + (n--) -1;
        while (n >= 0) {
            nums1[p--] = m < 0 ? nums2[n--] : nums1[m] > nums2[n] ? nums1[m--] : nums2[n--];
        }
    }


    //*******************************************************************************************//

    /**
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/two-sum
     *
     * 解法分析
     * 解法一：暴力枚举
     * 遍历数组中的每一个数 x，寻找数组中是否存在 target-x，注意寻找(target-x)时应从x所在下标后面的位置去遍历查找，所以
     * 两层循环的条件分别是 for(int i = 0; i < nums.length-1; i++) 和 for(int j = i+1; j < nums.length; j++)
     * 解法二：哈希表，算法实现如下
     * 用空间换时间，创建一个哈希表，对于每一个 x，我们首先查询哈希表中是否存在 target-x，然后将 x 插入到哈希表中，即可保证不会让 x 和自己匹配
     *
     * @param nums 给定的整数数组
     * @param target 目标值
     * @return 满足和为target的数组值下标
     */
    public int[] twoSum(int[] nums, int target) {
        int len = nums.length;
        HashMap<Integer, Integer> hashMap = new HashMap<>(len - 1);
        hashMap.put(nums[0], 0);
        for (int i = 1; i < len; i++) {
            int another = target - nums[i];
            if (hashMap.containsKey(another)) {
                return new int[]{hashMap.get(another), i};
            }
            hashMap.put(nums[i], i);
        }
        throw new IllegalArgumentException("there are no two sum numbers.");
    }


    //*******************************************************************************************//

    /**
     * 去除数组中重复的数字
     *
     * @param nums 原数组
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int tempIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[tempIndex] != nums[i]) {
                tempIndex++;
                if (tempIndex != i) {
                    nums[tempIndex] = nums[i];
                }
            }
        }
        return tempIndex + 1;
    }


    //*******************************************************************************************//

    /**
     * https://leetcode-cn.com/problems/smallest-k-lcci/
     * 设计一个算法，找出数组中最小的k个数。以任意顺序返回这k个数均可。
     * <p>
     * 本解法是采用构建k个数的大顶堆，然后动态更新大顶堆，遍历完数组后大顶堆中的元素就是数组中最小的k个数了
     *
     * @param arr 原数组
     * @param k   k
     * @return 原数组中k个最小的数
     */
    public int[] smallestK(int[] arr, int k) {
        int[] ans = new int[k];
        if (arr == null || k < 1) return ans;
        int length = arr.length;
        if (length <= k) return arr;

        buildMaxRootHeap(arr, k);
        for (int i = k; i < arr.length; i++) {
            // 因为是用的大顶堆，这里的条件应为待比较的元素比堆顶元素小的时候，才替换堆顶元素并调整堆结构
            if (arr[i] < arr[0]) {
                arr[0] = arr[i];
                adjustHeap(arr, 0, k);
            }
        }
        System.arraycopy(arr, 0, ans, 0, k);
        return ans;
    }
    // 构建大顶堆
    private void buildMaxRootHeap(int[] array, int length) {
        for (int i = (length-2) >> 1; i >= 0; i--) {
            adjustHeap(array, i, length);
        }
    }
    // 调整堆顶元素，维持堆结构为大顶堆
    private void adjustHeap(int[] arr, int parent, int len) {
        int temp = arr[parent];
        int child = 2 * parent + 1;
        while (child < len) {
            if (child + 1 < len && arr[child] <= arr[child+1]) {
                child++;
            }
            if (temp >= arr[child]) {
                break;
            }
            arr[parent] = arr[child];
            parent = child;
            child = 2 * parent + 1;
        }
        arr[parent] = temp;
    }


    /**
     * https://leetcode-cn.com/problems/smallest-k-lcci/
     * 设计一个算法，找出数组中最小的k个数。以任意顺序返回这k个数均可。
     * <p>
     * 本解法采用小顶堆
     *
     * @param arr 原数组
     * @param k   k
     * @return 原数组中k个最小的数
     */
    public int[] smallestK_(int[] arr, int k) {
        int len = arr.length;
        if (k >= len) {
            return arr;
        }

        buildMinHeap(arr, len);

        int pos = len - k;
        for (int i = len - 1; i >= pos; i--) {
            swap(arr, 0, i);
            heapify(arr, 0, --len);
        }

        int[] ret = new int[k];
        System.arraycopy(arr, pos, ret, 0, k);
        return ret;
    }
    private void buildMinHeap(int[] arr, int len) {
        for (int i = (len - 1) / 2; i >= 0; i--) {
            heapify(arr, i, len);
        }
    }
    private void heapify(int[] arr, int i, int len) {
        if (i >= len) return;

        int min = i;
        int c1 = 2 * i + 1;
        int c2 = 2 * i + 2;

        if (c1 < len && arr[c1] < arr[min]) {
            min = c1;
        }
        if (c2 < len && arr[c2] < arr[min]) {
            min = c2;
        }

        if (min != i) {
            swap(arr, i, min);
            heapify(arr, min, len);
        }
    }
    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    //*******************************************************************************************//

    /*
    // 求最短区间的代码模板
    int minimalRange(int[] A) {
        final int N = A == null ? 0 : A.length;
        // 子串的左边，采用左开右闭原则(left, i]表示一个子串
        int left = -1;
        // 记录最短的子串的长度
        int ans = A.length + 1;
        for (int i = 0; i < N; i++) {
            // 注意 在加入A[i]之前，(left, i-1]可能不满足条件!
            // step 1. 直接将A[i]加到区间中，形成(left, i]
            // step 2. TODO 更新区间的状态
            while (区间超出/满足条件) {
                ans = Math.min(ans, i - left);
                // step 3. 移除A[++left];
                // step 4. TODO 更新区间的状态
            }
            // assert ! 区间(left, i]到这里肯定不满足条件
        }
        return ans;
    }


    // 求定长区间的代码模板
    int fixedLength(int[] A, int windowSize) {
        final int N = A == null ? 0 : A.length;
        int left = -1;
        for (int i = 0; i < N; i++) {
            // step 1. 直接将A[i]加到区间中，形成(left, i]
            // TODO 修改区间的状态
            // 如果滑动窗口还太小
            if (i - left < windowSize) {
                continue;
            }
            // assert 此时(left, i]长度必然等于windowSize
            // TODO 判断区间的状态是否满足约束条件
            left++;
            // step 2. 移除A[left]
            // TODO 修改区间状态
        }
        return ans; // 返回最优解
    }


    // 求最长区间的代码模板
    int maxLength(int[] A) {
        int N = A.length;
        // 区间的左指针
        int left = -1;
        int ans = 0;
        for (int i = 0; i < N; i++) {
            // assert 在加入A[i]之前，(left, i-1]是一个合法有效的区间
            // step 1. 直接将A[i]加到区间中，形成(left, i]
            // step 2. 将A[i]加入之后，惰性原则
            // TODO 检查区间状态是否满足条件
            while (check((left, i]))) {
                ++left; // 如果不满足条件，移动左指针
                // TODO 修改区间的状态
            }
            // assert 此时(left, i]必然满足条件
            ans = max(ans, i - left);
        }
        return ans; // 返回最优解
    }
    */

}
