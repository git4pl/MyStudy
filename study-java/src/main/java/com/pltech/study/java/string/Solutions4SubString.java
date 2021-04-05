package com.pltech.study.java.string;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by Pang Li on 2021/4/4
 */
public class Solutions4SubString {
    public static void main(String[] args) {
        Solutions4SubString obj = new Solutions4SubString();
        int length = obj.lengthOfLongestSubstring("abcdc");
        System.out.println("the length is: " + length);
    }

    /**
     * 找出一个字符串 s 中无重复字符子串的长度。
     */
    public int lengthOfLongestSubstring(String s) {
        final int N = s == null ? 0 : s.length();
        int[] pos = new int[256];
        Arrays.fill(pos, -1);
        int answer = 0;
        int left = -1;
        for (int i = 0; i < N; i++) {
            final int idx = s.charAt(i);
            while (pos[idx] > left) {
                left = pos[idx];
            }
            pos[idx] = i;
            answer = Math.max(answer, i - left);
        }
        return answer;
    }

    /**
     * 字符串中只有字符'('和')'。合法字符串需要括号可以配对，判断字符串括号是否合法
     *
     * @param s 字符串
     */
    public boolean isValid1(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        if (s.length() % 2 == 1) {
            return false;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    /**
     * 对上述方法1进行优化，栈中元素相同时，没必要使用栈，只需要记录元素个数
     *
     * @param s 只有字符'('和')'的字符串
     */
    public boolean isValid2(String s) {
        // 当字符串本来就是空的时候，我们可以快速返回true
        if (s == null || s.length() == 0) {
            return true;
        }

        // 当字符串长度为奇数的时候，不可能是一个有效的合法字符串
        if (s.length() % 2 == 1) {
            return false;
        }

        // 消除法的主要核心逻辑:
        int leftBraceNumber = 0;
        for (int i = 0; i < s.length(); i++) {
            // 取出字符
            char c = s.charAt(i);
            if (c == '(') {
                // 如果是'('，那么压栈
                leftBraceNumber++;
            } else if (c == ')') {
                // 如果是')'，那么就尝试弹栈
                if (leftBraceNumber <= 0) {
                    // 如果弹栈失败，那么返回false
                    return false;
                }
                --leftBraceNumber;
            }
        }
        return leftBraceNumber == 0;
    }


}
