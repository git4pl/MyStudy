package com.pltech.study.java.treenode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树相关算法
 * 与二叉树关联的面试问题一般都重点考察二叉树的各种遍历，二叉树的遍历方式主要有：前序遍历、中序遍历和后续遍历，
 * 而这些遍历方式都可以有递归实现和迭代实现。
 * 遍历的时间复杂度均为O(n)，每个结点都只遍历一次，并且每个结点访问只需要O(1)复杂度的时间；
 * 空间复杂度为O(h)，其中h为树的高度。有一种Morris遍历的算法，其空间复杂度为O(1)。
 * <p>
 * Created by Pang Li on 2020/10/12
 */
public class Solutions4Traversal {

    /* *****************************前序遍历************************************** */

    /**
     * 递归方法实现先序遍历二叉树
     *
     * @param root 树根节点
     * @return 节点值的先序序列
     */
    public List<Integer> preOrderTraversal1(TreeNode root) {
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
    public List<Integer> preOrderTraversal2(TreeNode root) {
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
     * 迭代方法实现先序遍历二叉树
     */
    public List<Integer> preOrderTraversal3(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                result.add(root.val);
                root = root.left;
            }
            // 当无法压栈的时候，将root.right进行压栈
            root = stack.pop();
            root = root.right;
        }
        return result;
    }


    /* *****************************中序遍历************************************** */

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


    /* *****************************后序遍历************************************** */

    /**
     * 递归实现后续遍历二叉树
     *
     * @param root 二叉树根节点
     * @return 节点值的后序序列
     */
    public List<Integer> postOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postOrder(root, result);
        return result;
    }

    private void postOrder(TreeNode root, List<Integer> ans) {
        if (root != null) {
            // 先遍历左子树
            postOrder(root.left, ans);
            // 最后遍历右子树
            postOrder(root.right, ans);
            // 然后遍历中间的根结点
            ans.add(root.val);
        }
    }

    /**
     * 迭代方式实现后序遍历二叉树
     *
     * @param root 二叉树根节点
     * @return 节点值的后续序列
     */
    public List<Integer> postOrderTraversal1(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode preNode = null; //记录前一个被遍历的节点
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            // 顺着左子树走，将所有左子节点入栈
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            // 当没有左子节点时，考察栈顶节点，注意此时不要出栈
            // 因为此时的栈顶节点可能有最子树没有遍历
            root = stack.peek();
            // 如果要遍历当前结点，需要确保右子树已经遍历完毕
            // 1. 如果当前结点右子树为空，那么右子树没有遍历的必要，将当前结点放到result中
            // 2. 当root.right == preNode时，说明右子树已经被打印过了，此时要将当前结点放到result中
            if (root.right == null || root.right == preNode) {
                // 栈顶节点的右子树已遍历完毕，将此节点放入结果列表
                result.add(root.val);
                stack.pop();    // 将栈顶节点出栈
                preNode = root; // 更新前一个遍历的节点
                root = null;    //已经打印完毕。需要设置为空，否则下一轮循环还会遍历到其左子树
            } else {
                // 第一次走到root结点，不能放到result中，因为root的右子树还没有遍历,
                // 需要将root结点的右子树遍历
                root = root.right;
            }
        }
        return result;
    }


    /* *****************************Morris遍历*********************************** */

    /**
     * Morris 前序遍历
     *
     * @param root 二叉树根节点
     * @return 前序序列
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        TreeNode cur = root;
        List<Integer> ans = new ArrayList<>();

        while (cur != null) {
            if (cur.left != null) {
                TreeNode pre = cur.left;
                while (pre.right != null && pre.right != cur) {
                    pre = pre.right;
                }
                if (pre.right == null) {
                    ans.add(cur.val);
                    pre.right = cur;
                    cur = cur.left;
                } else {
                    pre.right = null;
                    cur = cur.right;
                }
            } else {
                ans.add(cur.val);
                cur = cur.right;
            }
        }
        return ans;
    }

    /**
     * Morris 中序遍历
     * 如果 xx 无左孩子，先将 xx 的值加入答案数组，再访问 xx 的右孩子，即 x = x.right。
     * 如果 xx 有左孩子，则找到 xx 左子树上最右的节点（即左子树中序遍历的最后一个节点，xx 在中序遍历中的前驱节点），我们记为predecessor。根据predecessor 的右孩子是否为空，进行如下操作。
     * 如果 predecessor 的右孩子为空，则将其右孩子指向 xx，然后访问 xx 的左孩子，即 x = x.left。
     * 如果 predecessor 的右孩子不为空，则此时其右孩子指向 xx，说明我们已经遍历完 xx 的左子树，我们将 predecessor 的右孩子置空，将 xx 的值加入答案数组，然后访问 xx 的右孩子，即 x = x.right。
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

    //BFS 算法通用代码模板
//    bfs(s) { // s表示出发点
//        q = new queue()
//        q.push(s), visited[s] = true // 标记s为已访问
//        while (!q.empty()) {
//            u = q.pop() // 拿到当前结点
//            for next in getNext(u) { // 拿到u的后继next
//                if (!visited[next]) { // 如果next还没有访问过
//                    q.push(next)
//                    visited[next] = true
//                }
//            }
//        }
//    }

/** 在矩阵中某点可以斜着走，一共有8个方向，定义如下
 int[][] dir = {
 {0, 1},  // 右
 {0, -1}, // 左
 {1, 0},  // 下
 {-1, 0}, // 上
 {-1,-1}, // 左上
 {-1,1},  // 右上
 {1,-1},  // 左下
 {1,1},   // 右下
 };
 for(int d = 0;d<8;d++) {
 int nr = r + dir[d][0];
 int nc = c + dir[d][1];
 if (!(nr < 0 || nc < 0 || nr >= R || nc >= C)) {
 // 处理点 (nr, nc)
 }
 }
 **/

    //********************************二叉树层序遍历***************************************//

    /**
     * https://leetcode-cn.com/problems/binary-tree-zigzag-level-order-traversal/
     * 给你二叉树的根节点 root ，返回其节点值的锯齿形层序遍历。
     * 即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行。
     *
     * @param root TreeNode
     * @return 层序遍历结果列表
     */
    // 用 一个队列 + 一个栈 实现
    public List<List<Integer>> zigzagLevelOrder1(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean isLeftToRight = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node != null) {
                    if (isLeftToRight) {
                        list.add(node.val);
                    } else {
                        stack.push(node);
                    }
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
            }
            if (!isLeftToRight) {
                while (!stack.isEmpty()) {
                    TreeNode tree = stack.pop();
                    list.add(tree.val);
                }
            }
            result.add(list);
            isLeftToRight = !isLeftToRight;
        }
        return result;
    }

    // 用两个栈实现
    public List<List<Integer>> zigzagLevelOrder2(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root);
        TreeNode node;

        while (true) {
            ArrayList<Integer> temp1 = new ArrayList<>();
            while (!stack1.isEmpty()) {
                node = stack1.pop();
                temp1.add(node.val);
                if (node.left != null) {
                    stack2.push(node.left);
                }
                if (node.right != null) {
                    stack2.push(node.right);
                }
            }

            if (!temp1.isEmpty()) {
                result.add(temp1);
            } else {
                break;
            }

            ArrayList<Integer> temp2 = new ArrayList<>();
            while (!stack2.isEmpty()) {
                node = stack2.pop();
                temp2.add(node.val);
                if (node.right != null) {
                    stack1.push(node.right);
                }
                if (node.left != null) {
                    stack1.push(node.left);
                }
            }

            if (!temp2.isEmpty()) {
                result.add(temp2);
            } else {
                break;
            }
        }
        return result;
    }

}
