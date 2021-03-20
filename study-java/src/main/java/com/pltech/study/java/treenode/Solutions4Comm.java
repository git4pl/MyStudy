package com.pltech.study.java.treenode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 一些常见的二叉树相关算法问题
 * Created by Pang Li on 2021/3/20
 */
public class Solutions4Comm {
    /**
     * 对二叉树镜像处理
     *
     * @param root 二叉树根节点
     * @return 新的二叉树
     */
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) return null;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);
            new TreeNode();
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
        }
        return root;
    }


    /*********************************************************************/

    private List<List<Integer>> results;
    private List<Integer> path;

    /**
     * 二叉树中和为某一值的所有节点路径
     *
     * @param root 二叉树根节点
     * @param sum  给定的和
     * @return 节点路径
     */
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if (root == null || sum == 0) return null;
        results = new ArrayList<>();
        path = new ArrayList<>();
        recursive(root, sum);
        return results;
    }

    private void recursive(TreeNode root, int tar) {
        if (root == null) return;
        path.add(root.val);
        tar -= root.val;
        if (tar == 0 && root.left == null && root.right == null) {
            results.add(new ArrayList<>(path));
        }
        recursive(root.left, tar);
        recursive(root.right, tar);
        path.remove(path.size() - 1);
    }
}
