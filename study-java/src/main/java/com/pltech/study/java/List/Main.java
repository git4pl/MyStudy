package com.pltech.study.java.List;

public class Main {
    public static void main(String[] args) {
        Node head = new Node();
        Node second = new Node();
        Node third = new Node();
        Node forth = new Node();
        Node fifth = new Node();
        head.val = 1;
        head.next = second;
        second.val = 2;
        second.next = third;
        third.val = 2;
        third.next = forth;
        forth.val = 1;
        forth.next = fifth;
        fifth.val = 1;
        fifth.next = null;

        Main objMain = new Main();
        boolean isBack = objMain.isPalindromeList(head);
        System.out.println("is Palindrome node list:" + isBack);
    }

    private int lenOfList = 0;

    public Node reverse(Node head) {
        if (head == null || head.next == null) {
            return null;
        }

        Node pre = null, next;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
            lenOfList++;
        }
        return pre;
    }

    /**
     * 检测链表是否是回文链表
     *
     * @param root 待检测的单链表
     * @return 是否为回文链表
     */
    public boolean isPalindromeList(Node root) {
        Node currOld = root.clone();
        Node currNew = reverse(root);
        //遍历链表，比较原链表与反转后的链表节点
        for (int i = 0; i < lenOfList - 1; i++) {
            if (currNew == null || currOld == null) {
                break;
            }
            if (currNew.val != currOld.val) {
                return false;
            } else {
                currNew = currNew.next;
                currOld = currOld.next;
            }
        }

        return true;
    }
}

class Node implements Cloneable {
    int val;
    Node next;

    @Override
    public Node clone() {
        try {
            Node clone = (Node) super.clone();
            //copy mutable state here, so the clone can't change the internals of the original
            if (this.next != null) {
                clone.next = this.next.clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}