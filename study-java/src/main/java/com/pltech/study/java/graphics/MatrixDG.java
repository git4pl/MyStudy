package com.pltech.study.java.graphics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class MatrixDG {

    private char[] mVexs;       // 顶点集合
    private int[][] mMatrix;    // 邻接矩阵

    /*
     * 创建图(自己输入数据)
     */
    public MatrixDG() {

        // 输入"顶点数"和"边数"
        System.out.print("input vertex number: ");
        int vlen = readInt();
        System.out.print("input edge number: ");
        int elen = readInt();
        if (vlen < 1 || elen < 1 || (elen > (vlen * (vlen - 1)))) {
            System.out.print("input error: invalid parameters!\n");
            return;
        }

        // 初始化"顶点"
        mVexs = new char[vlen];
        for (int i = 0; i < mVexs.length; i++) {
            System.out.printf("vertex(%d): ", i);
            mVexs[i] = readChar();
        }

        // 初始化"边"
        mMatrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            System.out.printf("edge(%d):", i);
            char c1 = readChar();
            char c2 = readChar();
            int p1 = getPosition(c1);
            int p2 = getPosition(c2);

            if (p1 == -1 || p2 == -1) {
                System.out.print("input error: invalid edge!\n");
                return;
            }

            mMatrix[p1][p2] = 1;
        }
    }

    /*
     * 创建图(用已提供的矩阵)
     *
     * 参数说明：
     *     vexs  -- 顶点数组
     *     edges -- 边数组
     */
    public MatrixDG(char[] vexs, char[][] edges) {

        // 初始化"顶点数"和"边数"
        int vlen = vexs.length;
        //int elen = edges.length;

        // 初始化"顶点"
        mVexs = new char[vlen];
        System.arraycopy(vexs, 0, mVexs, 0, mVexs.length);

        // 初始化"边"
        mMatrix = new int[vlen][vlen];
        for (char[] edge : edges) {
            // 读取边的起始顶点和结束顶点
            int p1 = getPosition(edge[0]);
            int p2 = getPosition(edge[1]);

            mMatrix[p1][p2] = 1;
        }
    }

    /*
     * 返回ch位置
     */
    private int getPosition(char ch) {
        for (int i = 0; i < mVexs.length; i++)
            if (mVexs[i] == ch)
                return i;
        return -1;
    }

    /*
     * 读取一个输入字符
     */
    private char readChar() {
        char ch = '0';

        do {
            try {
                ch = (char) System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')));

        return ch;
    }

    /*
     * 读取一个输入字符
     */
    private int readInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /*
     * 打印矩阵队列图
     */
    public void print() {
        System.out.print("Martix Graph:\n");
        for (int i = 0; i < mVexs.length; i++) {
            for (int j = 0; j < mVexs.length; j++)
                System.out.printf("%d ", mMatrix[i][j]);
            System.out.print("\n");
        }
    }

    void topologySort(int[] vertexes, int[][] graph, int numVertexes) {
        int[] inDegrees = new int[numVertexes]; //所有节点的入度数
        int[] outDegrees = new int[numVertexes];//所有节点的出度数
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                inDegrees[i] += graph[j][i];
                outDegrees[i] += graph[i][j];
            }
        }
        //入度为0的结点的个数，也就是入队个数
        int number = 0;
        //用队列保存拓扑序列
        Queue<Integer> queue = new LinkedList<>();
        //入度数不为0的节点
        Queue<Integer> nonZero = new LinkedList<>();
        //暂时存放拓扑序列
        Queue<Integer> temp = new LinkedList<>();
        //遍历图中所有结点，找入度为0的结点删除（放进队列）
        for (int i = 0; i < numVertexes; i++) {
            if (inDegrees[i] == 0) {
                queue.offer(i);
            } else {
                nonZero.offer(i);
            }
        }
        ArrayList<String> paths = new ArrayList<>();
        //删除这些被删除结点的出边（即对应结点入度减一）
        while (!queue.isEmpty()) {
            int i = queue.peek();
            temp.offer(queue.poll());
            outDegrees[i] = 0;
            number++;
            for (int j = 0; j < numVertexes; j++) {
                //从节点i到节点j有路径
                if (graph[i][j] == 1) {
                    inDegrees[j] -= 1;
                    //出现了新的入度为0的结点，删除
                    if (inDegrees[j] == 0) {
                        queue.offer(j);
                        nonZero.remove(j);
                    }
                }
            }
        }

        Stack<Integer> stack = new Stack<>();//用栈保存未遍历完的行
        if (number != numVertexes) {
            System.out.println("\n最后存在入度为1的结点，这个有向图是有回路的:");
            int lastJ;
            int times;
            while (!nonZero.isEmpty()) {
                int i = nonZero.peek();
                StringBuilder sb = new StringBuilder();
                String path;
                times = 0;
                stack.push(i);
                sb.append(vertexes[i]);
                if (outDegrees[i] == 0) {
                    stack.clear();
                    nonZero.poll();
                    continue;
                }
                //遍历第i行的元素
                for (int j = 0; j < numVertexes; ) {
                    if (graph[i][j] != 0) {
                        sb.append(" -> ").append(vertexes[j]);
                        lastJ = j;
                        stack.push(j);
                        if (!nonZero.isEmpty() && j == nonZero.peek()) {
                            path = sb.toString();
                            paths.add(path);
                            graph[i][j] = 0; //断开一条边，破环一个环
                            sb.delete(0, sb.length());
                            times++;
                            if (times == inDegrees[j]) {
                                break;
                            }
                            j++;
                        } else {
                            j = 0;
                        }
                        i = lastJ;
                        //如果第i个节点第出度为0，则直接跳过此行的遍历了
                        if (outDegrees[i] == 0) {
                            stack.pop();
                            i = stack.peek();
                            j = lastJ + 1;
                            sb.delete(sb.lastIndexOf(" -> "), sb.length());
                        }
                    } else {
                        j++;
                        if (j == numVertexes && stack.size() > 1) {
                            j = i + 1;
                            //回溯
                            stack.pop();
                            i = stack.peek();
                            sb.delete(sb.lastIndexOf(" -> "), sb.length());
                        }
                    }
                }
                stack.clear();
                nonZero.poll();
            }

            for (String path : paths) {
                System.out.println(path);
            }
        } else {
            System.out.println("\n这个有向图不存在回路，拓扑序列为：" + temp.toString());
        }
    }

    public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        char[][] edges = new char[][]{
                {'A', 'B'},
                {'B', 'C'},
                {'B', 'E'},
                {'C', 'E'},
                {'C', 'B'},
                {'D', 'C'},
                {'E', 'B'},
                {'E', 'D'},
                {'F', 'G'}};
        MatrixDG pG;

        // 自定义"图"(输入矩阵队列)
        //pG = new MatrixDG();
        // 采用已有的"图"
        pG = new MatrixDG(vexs, edges);

        pG.print();   // 打印图

        pG.topologySort(nodes, map, nodes.length);
    }

    static int[][] map = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1},
            {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0}
    };
    //static int[] nodes = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
    //        28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40};
    static int[] nodes = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
}