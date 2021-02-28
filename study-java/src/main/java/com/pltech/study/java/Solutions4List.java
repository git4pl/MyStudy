package com.pltech.study.java;

/**
 * 与链表操作相关的算法
 * Created by Pang Li on 2021/2/28
 */
public class Solutions4List {
    private static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     * <p>
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/add-two-numbers
     *
     * @param l1 链表
     * @param l2 链表
     * @return 求和得到的链表
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode currNode = new ListNode();
        ListNode resNode = currNode;
        int carry = 0; // 记录进位值
        while (l1 != null || l2 != null || carry > 0) {
            int n1 = 0, n2 = 0;
            if (l1 != null) {
                n1 = l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                n2 = l2.val;
                l2 = l2.next;
            }
            int sum = n1 + n2 + carry;
            currNode.next = new ListNode(sum % 10);
            carry = sum / 10;
            currNode = currNode.next;
        }
        // 因为第一个求和得到的节点是初始化的链表头节点的next节点（见line52的赋值）
        // 所以这里要返回next节点
        return resNode.next;
    }
}
