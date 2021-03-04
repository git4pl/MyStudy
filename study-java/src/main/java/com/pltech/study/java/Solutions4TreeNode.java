package com.pltech.study.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 二叉树相关算法
 * Created by Pang Li on 2020/10/12
 */
public class Solutions4TreeNode {

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
        preorder(result, root);
        return result;
    }
    private void preorder(List<Integer> list, TreeNode root) {
        if (root == null) {
            return;
        }
        list.add(root.val);
        preorder(list, root.left);
        preorder(list, root.right);
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
        inorder(result, root);
        return result;
    }
    private void inorder(List<Integer> result, TreeNode tree) {
        if (tree == null) return;
        inorder(result, tree.left);
        result.add(tree.val);
        inorder(result, tree.right);
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

    /**
     * Morris 中序遍历
     * 如果 xx 无左孩子，先将 xx 的值加入答案数组，再访问 xx 的右孩子，即 x = x.right。
     * 如果 xx 有左孩子，则找到 xx 左子树上最右的节点（即左子树中序遍历的最后一个节点，xx 在中序遍历中的前驱节点），我们记为predecessor。根据predecessor 的右孩子是否为空，进行如下操作。
     * 	如果 predecessor 的右孩子为空，则将其右孩子指向 xx，然后访问 xx 的左孩子，即 x = x.left。
     * 	如果 predecessor 的右孩子不为空，则此时其右孩子指向 xx，说明我们已经遍历完 xx 的左子树，我们将 predecessor 的右孩子置空，将 xx 的值加入答案数组，然后访问 xx 的右孩子，即 x = x.right。
     * 重复上述操作，直至访问完整棵树
     *
     * @param root 树根节点
     * @return 二叉树的中序序列
     */
    public List<Integer> inorderTraversal3(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode preNode;
        while (root != null) {
            if (root.left != null) {
                preNode = root.left;
                // 寻找root节点的左子树中的最右孩子节点，即中序序列中root节点的前驱节点
                while (preNode.right != null && preNode.right != root) {
                    preNode = preNode.right;
                }
                // 找到了root的左子树的最右孩子节点，将这个节点的右指针指向root，遍历root的左子树
                if (preNode.right == null) {
                    preNode.right = root;
                    root = root.left;
                } else { // 左子树访问完了，断开root前驱节点的右指针，遍历root的右子树
                    result.add(root.val);
                    preNode.right = null;
                    root = root.right;
                }
            } else { // 当前节点没有左孩子，将节点的值加入结果序列，继续访问其右孩子
                result.add(root.val);
                root = root.right;
            }
        }
        return result;
    }
}
