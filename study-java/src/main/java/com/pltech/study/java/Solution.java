package com.pltech.study.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 二叉树相关算法
 * Created by Pang Li on 2020/10/12
 */
public class Solution {

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
        traversePreOrder(result, root);
        return result;
    }
    private void traversePreOrder(List<Integer> list, TreeNode root) {
        if (root == null) {
            return;
        }
        list.add(root.val);
        traversePreOrder(list, root.left);
        traversePreOrder(list, root.right);
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


    /**
     * 递归实现中序遍历二叉树
     *
     * @param root 树根节点
     * @return 节点值的中序序列
     */
    public List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        traverseInorder(result, root);
        return result;
    }
    private void traverseInorder(List<Integer> result, TreeNode tree) {
        if (tree == null) return;
        traverseInorder(result, tree.left);
        result.add(tree.val);
        traverseInorder(result, tree.right);
    }

    /**
     * 迭代方法实现中序遍历二叉树
     *
     * @param root 树根节点
     * @return 节点值的中序序列
     */
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            result.add(root.val);
            root = root.right;
        }
        return result;
    }

}
