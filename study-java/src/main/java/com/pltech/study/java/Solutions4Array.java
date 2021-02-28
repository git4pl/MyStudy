package com.pltech.study.java;

import java.util.HashMap;

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
