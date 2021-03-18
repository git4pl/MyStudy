package com.pltech.study.java;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Description:
 * 收集LeetCode上一些有意义的问题的解法
 *
 * @author Alex Pang(pl88cn@126.com)
 * @since 2021/3/4 22:26
 */
class Solutions {

    public static void main(String[] args) {
        int[] heights = {2, 1, 5, 6, 2, 3};
        //int[] heights = {1, 1};
        Solutions solution = new Solutions();
        int res = solution.largestRectangleArea2(heights);
        System.out.println(res);
    }

    /**
     * https://leetcode-cn.com/problems/largest-rectangle-in-histogram/
     * 84. 柱状图中最大的矩形
     * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1。
     * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
     * <p>
     * 解法一：
     * 暴力解法，枚举以每个柱形为高度的最大矩形的面积。
     * 依次遍历柱形的高度，对于每一个高度分别向两边扩散，求出以当前高度为矩形的最大宽度多少。
     * 时间复杂度为 O(n^2)
     * <p>
     * 解法二：
     * 单调栈，以空间换时间
     * 具体解法描述详见leetcode题解，算法实现如下
     *
     * @param heights 柱子高度数组
     * @return 最大矩形面积
     */
    public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        if (len == 0) {
            return 0;
        }
        if (len == 1) {
            return heights[0];
        }

        int area = 0;
        // 在数组前后各家一个值为0的哨兵元素
        int[] newHeights = new int[len + 2];
        System.arraycopy(heights, 0, newHeights, 1, len);
        len += 2;
        heights = newHeights;

        Deque<Integer> stack = new ArrayDeque<>();
        stack.addLast(0);//初始化栈底元素为0，即第一个哨兵元素

        for (int i = 1; i < len; i++) {
            while (heights[stack.peekLast()] > heights[i]) {
                int height = heights[stack.removeLast()];
                int width = i - stack.peekLast() - 1;
                area = Math.max(area, height * width);
            }
            stack.addLast(i);
        }
        return area;
    }

    public int largestRectangleArea2(int[] heights) {
        Deque<Integer> stack = new LinkedList<>();//存的是下标
        stack.addFirst(-1);
        int res = 0;
        for (int i = 0; i < heights.length; i++) {
            //当前柱子高度低于栈顶柱子高度时，出栈
            while (stack.peekFirst() != -1 && heights[stack.peekFirst()] > heights[i]) {
                //注意，计算面积时，宽度是从当前柱子到前一个低于自己的柱子为止，而不是仅算自己的柱子，所以现在栈中添加了-1
                //这样算最矮的柱子时，宽度是整个柱子堆
                res = Math.max(res, heights[stack.removeFirst()] * (i - stack.peekFirst() - 1));
            }
            stack.addFirst(i);
        }
        //最后栈中剩下的是从矮到高的柱子，并且最后一个柱子一定在栈中
        while (stack.peekFirst() != -1) {
            res = Math.max(res, heights[stack.removeFirst()] * (heights.length - stack.peekFirst() - 1));
        }
        return res;
    }
}
