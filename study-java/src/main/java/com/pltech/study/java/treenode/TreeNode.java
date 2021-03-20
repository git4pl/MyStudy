package com.pltech.study.java.treenode;

/**
 * 二叉树是指树中节点的度不大于2的有序树，所以二叉树的节点中存有值（val）、左子节点（left）和右子节点（right）
 * Created by Pang Li on 2021/3/20
 */
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

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
