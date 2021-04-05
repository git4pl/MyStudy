package com.pltech.study.java;

import java.util.Stack;

/**
 * Created by Pang Li on 2021/4/5
 */
public class Solutions4Stack {
    /**
     * 在水中有许多鱼，可以认为这些鱼停放在 x 轴上。再给定两个数组 Size，Dir，Size[i] 表示第 i 条鱼的大小，
     * Dir[i] 表示鱼的方向 （0 表示向左游，1 表示向右游）。这两个数组分别表示鱼的大小和游动的方向，并且两个
     * 数组的长度相等。鱼的行为符合以下几个条件:
     * 1. 所有的鱼都同时开始游动，每次按照鱼的方向，都游动一个单位距离；
     * 2. 当方向相对时，大鱼会吃掉小鱼；
     * 3. 鱼的大小都不一样。
     *
     * @param fishSize      鱼的数量
     * @param fishDirection 鱼儿游动的方向
     * @return
     */
    public int solution(int[] fishSize, int[] fishDirection) {
        //获取鱼儿数量，如果鱼的数量小于等于1，那么直接返回鱼的数量
        final int fishNumber = fishSize.length;
        if (fishNumber < 1) {
            return fishNumber;
        }
        //0 表示向左游，1 表示向右游
        final int left = 0;
        final int right = 1;
        Stack<Integer> t = new Stack<>();
        for (int i = 0; i < fishNumber; i++) {
            //当前鱼的情况：游动方向和大小
            final int curFishDirection = fishDirection[i];
            final int curFishSize = fishSize[i];
            //当前鱼是否被栈中鱼吃掉
            boolean beenEaten = false;
            //如果栈中还有鱼，并且栈中鱼向右，当前的鱼向左游，那么就会有相遇的可能性
            while (!t.isEmpty() && fishDirection[t.peek()] == right && curFishDirection == left) {
                //如果栈顶的鱼比较大，那么把新来的吃掉
                if (fishSize[t.peek()] > curFishSize) {
                    beenEaten = true;
                    break;
                }
                //如果栈中的鱼较小，那么会把栈中的鱼吃掉，栈中的鱼被消除，所以需要弹栈
                t.pop();
            }
            //如果新来的鱼，没有被吃掉，那么压入栈中。
            if (!beenEaten) {
                t.push(i);
            }
        }
        return t.size();
    }

    /**
     * 一个整数数组 A，找到每个元素：右边第一个比我小的下标位置，没有则用 -1 表示。
     */
    public int[] findRightSmall(int[] A) {
        int[] ans = new int[A.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < A.length; i++) {
            final int x = A[i];
            //每个元素都向左遍历栈中的元素完成消除动作
            while (!stack.empty() && A[stack.peek()] > x) {
                ans[stack.peek()] = i;
                stack.pop();
            }
            stack.push(i);
        }
        //栈中剩下的元素，由于没有人能消除他们，因此，只能将结果设置为-1
        while (!stack.empty()) {
            ans[stack.peek()] = -1;
            stack.pop();
        }
        return ans;
    }

    /**
     * 给定一个正整数数组和 k，要求依次取出 k 个数，输出其中数组的一个子序列，需要满足：1.长度为 k；2.字典序最小。
     *
     * @param nums 数组
     * @param k    k个数
     */
    public int[] findSmallSeq(int[] nums, int k) {
        int[] ans = new int[k];
        Stack<Integer> s = new Stack<>();
        // 这里生成单调栈
        for (int i = 0; i < nums.length; i++) {
            final int x = nums[i];
            final int left = nums.length - i;

            // 注意我们想要提取出k个数，所以注意控制扔掉的数的个数
            while (!s.empty() && (s.size() + left > k) && s.peek() > x) {
                s.pop();
            }
            s.push(x);
        }

        // 如果递增栈里面的数太多，那么我们只需要取出前k个就可以了。
        // 多余的栈中的元素需要扔掉。
        while (s.size() > k) {
            s.pop();
        }

        // 把k个元素取出来，注意这里取的顺序!
        for (int i = k - 1; i >= 0; i--) {
            ans[i] = s.peek();
            s.pop();
        }

        return ans;
    }
}
