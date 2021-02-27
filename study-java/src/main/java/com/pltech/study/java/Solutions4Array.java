package com.pltech.study.java;

/**
 * 数组操作相关的LeetCode题目
 * Created by Pang Li on 2021/2/27
 */
public class Solutions4Array {

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
}
