package com.pltech.study.java.graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class AdjacentMatrix {

    private ArrayList<String> vexs; // 顶点表
    private int[][] edges; // 边表
    int numVertexes;
    int numEdges;
    boolean[] visited;

    public AdjacentMatrix(int numVertexes, int numEdges) {
        this.numVertexes = numVertexes;
        this.numEdges = numEdges;
        this.vexs = new ArrayList<String>(numVertexes);
        this.edges = new int[numVertexes][numVertexes];
        this.visited = new boolean[numVertexes];
    }

    private void insertVex(String v) {
        vexs.add(v);
    }

    private void insertEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
    }

    private void show() {
        for (int[] link : edges) {
            System.out.println(Arrays.toString(link));
        }
    }

    private void DFS(int i) {
        visited[i] = true;
        System.out.print(vexs.get(i) + " ");
        for (int j = 0; j < numVertexes; j++) {
            if (edges[i][j] > 0 && !visited[j]) {
                DFS(j);
            }
        }
    }

    private void DFSTraverse() {
        int i;
        for (i = 0; i < numVertexes; i++) {
            visited[i] = false;
        }
        for (i = 0; i < numVertexes; i++) {
            if (!visited[i]) {
                DFS(i);
            }
        }
    }

    private void BFSTraverse() {
        int i, j;
        LinkedList<Integer> queue = new LinkedList<>();
        for (i = 0; i < numVertexes; i++) {
            visited[i] = false;
        }
        for (i = 0; i < numVertexes; i++) {
            if (!visited[i]) {
                visited[i] = true;
                System.out.print(vexs.get(i) + " ");
                queue.addLast(i);
                while (!queue.isEmpty()) {
                    i = (Integer) queue.removeFirst();
                    for (j = 0; j < numVertexes; j++) {
                        if (edges[i][j] > 0 && !visited[j]) {
                            visited[j] = true;
                            System.out.print(vexs.get(j) + " ");
                            queue.addLast(j);
                        }
                    }
                }
            }
        }
    }

    void topologySort(ArrayList<String> vertexes, int[][] graph, int numVertexes) {
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
                sb.append(vertexes.get(i));
                if (outDegrees[i] == 0) {
                    stack.clear();
                    nonZero.poll();
                    continue;
                }
                //遍历第i行的元素
                for (int j = 0; j < numVertexes; ) {
                    if (graph[i][j] != 0) {
                        sb.append(" -> ").append(vertexes.get(j));
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
        int numVertexes = 9;
        int numEdges = 15;
        AdjacentMatrix graph = new AdjacentMatrix(numVertexes, numEdges);
        graph.insertVex("A");
        graph.insertVex("B");
        graph.insertVex("C");
        graph.insertVex("D");
        graph.insertVex("E");
        graph.insertVex("F");
        graph.insertVex("G");
        graph.insertVex("H");
        graph.insertVex("I");
        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 5, 1);
        graph.insertEdge(1, 2, 1);
        graph.insertEdge(1, 6, 1);
        graph.insertEdge(1, 8, 1);
        graph.insertEdge(2, 3, 1);
        graph.insertEdge(2, 8, 1);
        graph.insertEdge(3, 4, 1);
        graph.insertEdge(3, 6, 1);
        graph.insertEdge(3, 7, 1);
        graph.insertEdge(3, 8, 1);
        graph.insertEdge(4, 7, 1);
        graph.insertEdge(4, 5, 1);
        graph.insertEdge(5, 6, 1);
        graph.insertEdge(6, 7, 1);
        System.out.println("邻接矩阵");
        graph.show();
        System.out.print("深度优先遍历：");
        graph.DFSTraverse();
        System.out.println();
        System.out.print("广度优先遍历：");
        graph.BFSTraverse();

        //graph.topologySort(graph.edges, numVertexes);
        graph.topologySort(graph.vexs, graph.edges, numVertexes);
    }

}