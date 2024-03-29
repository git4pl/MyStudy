package com.pltech.study.java;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

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
        //int res = solution.trap3(heights);
        //solution.test1();
        int res = solution.maxNumLowerThanN(324312, new int[]{2, 4, 8});
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


    ///******************************************************************************************///

    /**
     * 给定一个直方图(也称柱状图)，假设有人从上面源源不断地倒水，最后直方图能存多少水量?直方图的宽度为 1。
     * https://leetcode-cn.com/problems/volume-of-histogram-lcci/
     * <p>
     * 解法一：动态规划
     * 创建两个长度为 n 的数组 leftMax 和 rightMax。对于 0 <= i < n，leftMax[i] 表示下标 i 及其左边的
     * 位置中height 的最大高度，rightMax[i] 表示下标 i 及其右边的位置中height 的最大高度。
     * leftMax[0]=height[0]，rightMax[n−1]=height[n−1]
     * 当 1 ≤ i ≤ n−1 时，leftMax[i]=max(leftMax[i−1], height[i])；
     * 当 0 ≤ i ≤ n−2 时，rightMax[i]=max(rightMax[i+1], height[i])。
     *
     * <p>
     * 解法二：单调栈
     * <p>
     * 单调栈存储的是下标，满足从栈底到栈顶的下标对应的数组 \textit{height}height 中的元素递减.
     * 从左到右遍历数组，遍历到下标 i 时，如果栈内至少有两个元素，记栈顶元素为 top，top 的下面一个元素是 left，
     * 则一定有 height[left] ≥ height[top]。如果 height[i] > height[top]，则得到一个可以接雨水的区域，
     * 该区域的宽度是 i − left − 1，高度是min(height[left], height[i]) − height[top]，
     * 根据宽度和高度即可计算得到该区域能接的水的量。
     * <p>
     * 为了得到 left，需要将 top 出栈。在对 top 计算能接的水的量之后，left 变成新的 top，重复上述操作，
     * 直到栈变为空，或者栈顶下标对应的height 中的元素大于或等于 height[i]。
     * <p>
     * 在对下标 i 处计算能接的水的量之后，将 i 入栈，继续遍历后面的下标，计算能接的水的量。
     * 遍历结束之后即可得到能接的水的总量。
     * <p>
     * <p>
     * 解法三：双指针
     * 维护两个指针 left 和 right，以及两个变量 leftMax 和 rightMax，初始时 left=0,right=n−1,leftMax=0,rightMax=0。
     * 指针 left 只会向右移动，指针 right 只会向左移动，在移动指针的过程中维护两个变量 leftMax 和rightMax 的值。
     */
    public int trap1(int[] height) {
        int len = height.length;
        if (len <= 2) return 0;
        int[] leftMax = new int[len];
        int[] rightMax = new int[len];
        leftMax[0] = height[0];
        rightMax[len - 1] = height[len - 1];
        for (int i = 1; i < len; i++) {
            leftMax[i] = Math.max(height[i], leftMax[i - 1]);
        }
        for (int j = len - 2; j >= 0; j--) {
            rightMax[j] = Math.max(height[j], rightMax[j + 1]);
        }
        int result = 0;
        for (int k = 1; k < len - 1; k++) {
            int t = Math.min(leftMax[k - 1], rightMax[k + 1]) - height[k];
            result += Math.max(t, 0);
        }
        return result;
    }

    public int trap2(int[] height) {
        int answer = 0;
        Deque<Integer> stack = new LinkedList<>();
        int n = height.length;
        for (int i = 0; i < n; ++i) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                int left = stack.peek();
                int currWidth = i - left - 1;
                int currHeight = Math.min(height[left], height[i]) - height[top];
                answer += currWidth * currHeight;
            }
            stack.push(i);
        }
        return answer;
    }

    public int trap3(int[] height) {
        int answer = 0;
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        while (left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if (height[left] < height[right]) {
                answer += leftMax - height[left];
                ++left;
            } else {
                answer += rightMax - height[right];
                --right;
            }
        }
        return answer;
    }


    ///**************************************************************************************///

    /**
     * 给定一个数组，表示不同的木板的高度，在装水的时候，你可以选择两根木板，然后装满水，在不能倾斜的情况下，
     * 里面能装多少水，应该由较短的木板决定。请问最多能装多少水？
     *
     * @param A 数组
     */
    public int maxArea(int[] A) {
        int answer = 0;
        final int N = A == null ? 0 : A.length;
        int i = 0, j = N - 1;
        while (i < j) {
            int width = j - i;
            int height = Math.min(A[i], A[j]);
            answer = Math.max(answer, width * height);
            if (A[i] > A[j]) {
                j--;
            } else {
                i++;
            }
        }

        return answer;
    }


    ///**************************************************************************************///

    /**
     * 输入一个数组nums和一个正整数k，请你判断nums是否能够被平分为元素和相同的k个子集。
     *
     * @param nums 数组
     * @param k    子集数
     */
    public boolean canPartitionKSubsets(int[] nums, int k) {
        if (k > nums.length) return false;
        int sum = 0;
        for (int v :
                nums) {
            sum += v;
        }
        if (sum % k != 0) return false;

        boolean[] used = new boolean[nums.length];
        int target = sum / k;
        return backtrack(k, 0, nums, 0, used, target);
    }

    /**
     * 递归回溯将数据装入桶中
     *
     * @param k      桶的序号
     * @param bucket 桶里的数据之和
     * @param nums   待装入的数据
     * @param start  在nums数组中待装入的数字下标
     * @param used   标记数字是否已装入桶中
     * @param target 一个桶里需要达到的数字和
     * @return 是否能按条件装桶
     */
    private boolean backtrack(int k, int bucket, int[] nums, int start, boolean[] used, int target) {
        if (k == 0) {
            //因为是从第k号桶开始装的，k==0 时表示所有桶都已被装满了，而且 nums 一定全部用完了
            return true;
        }
        if (bucket == target) {
            //当前桶已被装满，递归穷举下一个桶的选择
            //下一个桶也从 nums[0] 开始选数字
            return backtrack(k - 1, 0, nums, 0, used, target);
        }

        //从 start 开始向后探查有效的 nums[i] 装入当前桶
        for (int i = start; i < nums.length; i++) {
            if (used[i]) {
                //nums[i]已经被装入了其他的桶中
                continue;
            }
            if (nums[i] + bucket > target) {
                //当前桶装不下nums[i]
                continue;
            }
            used[i] = true;
            bucket += nums[i];
            //递归穷举下一个数字是否可以装入当前桶
            if (backtrack(k, bucket, nums, i + 1, used, target)) {
                return true;
            }
            //下一个数字不能装入当前桶，需撤销选择
            used[i] = false;
            bucket -= nums[i];
        }
        //穷举了所有的数字，都无法装满当前桶
        return false;
    }


    ///**************************************************************************************///

    int[] memo;

    /**
     * 【零钱兑换】给定不同面额的硬币 coins 和一个总金额 amount。
     * 编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
     *
     * @param coins  可兑换的钱币
     * @param amount 需兑换的钱数
     * @return 兑换方案数
     */
    public int coinChange(int[] coins, int amount) {
        if (coins.length == 0) {
            return -1;
        }
        memo = new int[amount];

        return findWay(coins, amount);
    }

    /**
     * memo[n] 表示钱币n可以被换取的最少的硬币数，不能换取就为-1
     * findWay函数的目的是为了找到 amount数量的零钱可以兑换的最少硬币数量，返回其值int
     *
     * @param coins  可兑换的钱币
     * @param amount 需兑换的钱数
     * @return 兑换方法数
     */
    private int findWay(int[] coins, int amount) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        if (memo[amount - 1] != 0) {
            return memo[amount - 1];
        }
        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            int res = findWay(coins, amount - coin);
            if (res > 0 && res < min) {
                min = res + 1;
            }
        }
        memo[amount - 1] = (min == Integer.MAX_VALUE ? -1 : min);
        return memo[amount - 1];
    }

    /**
     * 【零钱兑换】给定不同面额的硬币 coins 和一个总金额 amount。
     * 编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
     */
    public int coinChange2(int[] coins, int amount) {
        if (coins.length == 0) return -1;
        int[] memo = new int[amount + 1];
        memo[0] = 0;
        for (int i = 1; i <= amount; i++) {
            int min = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (i - coin >= 0 && memo[i - coin] < min) {
                    min = memo[i - coin] + 1;
                }
            }
            memo[i] = min;
        }
        return memo[amount] == Integer.MAX_VALUE ? -1 : memo[amount];
    }


    ///**************************************************************************************///

    /**
     * https://leetcode-cn.com/problems/palindromic-substrings/
     * 给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。
     */
    public int countSubstrings(String s) {
        if (s == null) return 0;
        int n = s.length(), ans = 0;
        for (int i = 0; i < 2 * n - 1; i++) {
            int left = i / 2;
            int right = i / 2 + i % 2;
            while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
                --left;
                ++right;
                ++ans;
            }
        }
        return ans;
    }

    public int countSubstrings1(String s) {
        if (s == null) return 0;
        int n = s.length(), ans = 0;
        //便利回文中心点
        for (int i = 0; i < n; i++) {
            //j=0表示有一个中心点；j=1表示有两个中心点
            for (int j = 0; j <= 1; j++) {
                int left = i;
                int right = i + j;
                while (left >= 0 && right < n && s.charAt(left--) == s.charAt(right++)) {
                    ans++;
                }
            }
        }

        return ans;
    }

    public int countSubstrings2(String s) {
        int ans = 0;
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (j - i <= 1) {
                        dp[i][j] = true;
                        ans++;
                    } else if (dp[i + 1][j - 1]) {
                        dp[i][j] = true;
                        ans++;
                    }
                }
            }
        }
        return ans;
    }


    ///******************************************************************************************///
    /**
     * 给定一个数n如23121; 给定一组升序10以内的数字a如[2 4 9]，求由数组a中元素组成的小于n的最大数
     */
    int res = 0;
    public int maxNumLowerThanN(int N, int[] nums) {
        if (N <= 10) {
            for (int n : nums) {
                res = Math.max(res, n);
            }
            return res;
        }
        backtrack(0, 0, false, String.valueOf(N), nums);
        return res;
    }

    /**
     * 回溯算法，找到每一位的正确数字
     * @param index         遍历给定的数值的位下标（从左到右 0...n）
     * @param temp     当前确定的数，目标数据的前几位
     * @param pass          是否要跳过当前位
     * @param digits        待选的数字，要求是升序数组
     */
    private boolean backtrack(int index, int temp, boolean pass, String srcN, int[] digits) {
        if (index == srcN.length()) {
            res = temp;
            return true;
        }

        int next = index + 1;
        int length = digits.length;
        if (!pass) {
            int val = srcN.charAt(index) - '0';
            //从digits数组中由大到小遍历
            for (int i = length - 1; i >= 0; i--) {
                int digit = digits[i];
                if (val == digit) {
                    if (backtrack(next, temp * 10 + digit, false, srcN, digits)) {
                        return true;
                    }
                } else if (val > digit) {
                    if (backtrack(next, temp * 10 + digit, true, srcN, digits)) {
                        return true;
                    }
                }
            }

            if (index != 0) return false;
            return backtrack(next, temp, true, srcN, digits);
        } else {
            return backtrack(next, temp * 10 + digits[length - 1], true, srcN, digits);
        }
    }

}
