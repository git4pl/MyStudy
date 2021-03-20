package com.pltech.study.java.treenode;

import java.util.Stack;

/**
 * 验证二叉搜索树：给定一棵二叉树，判断它是否是二叉搜索树
 * 二叉搜索树的特点：
 * · 根结点的值大于所有的左子树结点的值
 * · 根结点的值小于所有的右子树结点的值
 * · 左右子树也必须满足以上特性
 * <p>
 * 本类中给出几种检查二叉搜索树的算法实现
 * Created by Pang Li on 2021/3/20
 */
public class Solutions4BST {

    /* *****************************前序遍历法********************************* */

    /**
     * 用前序遍历递归实现检查二叉搜索树
     *
     * @param root 二叉树根节点
     * @return 是否是二叉搜索树
     */
    public boolean isValidBST(TreeNode root) {
        return preOrder(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean preOrder(TreeNode root, long minVal, long maxVal) {
        if (root == null) {
            return true;
        }
        //每个节点如果超过这个范围，直接返回false
        if (root.val >= maxVal || root.val <= minVal) {
            return false;
        }
        //这里再分别以左右两个子节点分别判断，
        //左子树范围的最小值是minVal，最大值是当前节点的值，也就是root的值，因为左子树的值要比当前节点小
        //右子数范围的最大值是maxVal，最小值是当前节点的值，也就是root的值，因为右子树的值要比当前节点大
        return preOrder(root.left, minVal, root.val) && preOrder(root.right, root.val, maxVal);
    }


    /* *****************************中序遍历法********************************* */

    private TreeNode prev;

    /**
     * 用中序遍历递归实现检查二叉搜索树
     *
     * @param root 二叉树根节点
     * @return 是否是二叉搜索树
     */
    public boolean isValidBST2(TreeNode root) {
        if (root == null) {
            return true;
        }
        //访问左子树
        if (!isValidBST2(root.left)) {
            return false;
        }
        //访问当前节点：如果当前节点小于等于中序遍历的前一个节点直接返回false。
        if (prev != null && prev.val >= root.val) {
            return false;
        }
        prev = root;
        //访问右子树
        return isValidBST2(root.right);
    }

    /**
     * 用中序遍历迭代实现检查二叉搜索树
     *
     * @param root 二叉树根节点
     * @return 是否是二叉搜索树
     */
    public boolean isValidBST3(TreeNode root) {
        if (root == null) {
            return true;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (pre != null && root.val <= pre.val) {
                return false;
            }
            //保存前一个访问的结点
            pre = root;
            root = root.right;
        }
        return true;
    }


    /* *****************************中序遍历法********************************* */

    // 用来存放一棵树里面数值的区间
    static class Range {
        public Long min = Long.MAX_VALUE;
        public Long max = Long.MIN_VALUE;

        public Range() {
        }

        public Range(Long l, Long r) {
            min = l;
            max = r;
        }
    }

    private boolean answer = true;
    private final Range emptyRange = new Range();

    private Range postOrder(TreeNode root) {
        // 如果是空树，或者已经判断不是一棵二叉搜索树了
        // 那么就不需要再继续遍历了。
        if (root == null || !answer) {
            return emptyRange;
        }
        Range l = postOrder(root.left);
        Range r = postOrder(root.right);
        if (root.val <= l.max || root.val >= r.min) {
            answer = false;
            // 当不符合的时候，返回任意区间都是可以的
            return emptyRange;
        }
        // 需要取左子树最小值与当前结点的最小值。
        // 需要取右子树最大值与当前结点的最大值
        return new Range(Math.min(l.min, root.val),
                Math.max(r.max, root.val));
    }

    /**
     * 用后序遍历递归实现检查二叉搜索树
     *
     * @param root 二叉树根节点
     * @return 是否是二叉搜索树
     */
    public boolean isValidBST4(TreeNode root) {
        answer = true;
        postOrder(root);
        return answer;
    }


    /* **************************二叉搜索树的查询操作****************************** */

    /**
     * 在二叉搜索树中搜索一个值为val的节点
     *
     * @param root 二叉搜索树根节点
     * @param val  搜索的节点值
     * @return 值为val的节点
     */
    public TreeNode searchBST(TreeNode root, int val) {
        while (root != null) {
            if (root.val == val) {
                return root;
            } else if (root.val < val) {
                root = root.right;
            } else {
                root = root.left;
            }
        }
        return null;
    }


    /* **************************二叉搜索树的插入操作****************************** */

    /**
     * 在二叉搜索树中插入一个值为val的节点
     *
     * @param root 二叉搜索树
     * @param val  插入的节点value
     * @return 新的二叉搜索树
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (val < root.val) {
            root.left = insertIntoBST(root.left, val);
        } else if (val > root.val) {
            root.right = insertIntoBST(root.right, val);
        }

        return root;
    }


    /* **************************二叉搜索树的删除操作****************************** */

    private void swapValue(TreeNode a, TreeNode b) {
        int t = a.val;
        a.val = b.val;
        b.val = t;
    }

    /**
     * 在二叉搜索树中删除一个值为key的节点
     *
     * @param root 二叉搜索树
     * @param key  删除的节点value
     * @return 新的二叉搜索树
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            // 当前树只有一个结点，那么直接返回null
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left != null) {
                // 当前结点还有左子树
                // 那么需要从左子树中找个较大的值。
                TreeNode large = root.left;
                while (large.right != null) {
                    large = large.right;
                }
                // 交换再删除
                swapValue(root, large);
                root.left = deleteNode(root.left, key);
            } else {
                // 当前结点还有右子树
                TreeNode small = root.right;
                // 那么需要从右子树中找个较小的值
                while (small.left != null) {
                    small = small.left;
                }
                // 交换再删除
                swapValue(root, small);
                root.right = deleteNode(root.right, key);
            }
        }

        return root;
    }
}
