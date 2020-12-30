package com.wb.study;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Pang Li on 2020/10/12
 */
public class Solution {

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

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 递归方法实现先序遍历二叉树
     *
     * @param root 树根节点
     * @return 节点值的先序序列
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        traverse(result, root);
        return result;
    }

    private void traverse(List<Integer> list, TreeNode root) {
        if (root == null) {
            return;
        }
        list.add(root.val);
        traverse(list, root.left);
        traverse(list, root.right);
    }

    /**
     * 迭代方法实现先序遍历二叉树
     *
     * @param root 树根节点
     * @return 节点值的先序序列
     */
    public List<Integer> preOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            TreeNode node = stack.pop();
            if (node != null) {
                result.add(node.val);
                stack.push(node.right);
                stack.push(node.left);
            }
        }

        return result;
    }

}
