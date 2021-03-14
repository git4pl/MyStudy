package com.pltech.study.java.heap;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 用堆解决的一些问题
 * Created by Pang Li on 2021/3/14
 */
public class SolutionsByHeap {
    /**
     * 一辆汽车携带 startFuel 升汽油从位置 0 出发前往位置 target，按顺序有一系列加油站 stations。
     * 第 i 个加油站位于 stations[i][0]，可以加 stations[i][1] 升油（一个加油站只能加一次）。
     * 如果想要到达 target，输出最少加油次数。如果不能到达 target，那么返回 -1。
     *
     * @param target    目标位置
     * @param startFuel 开始的汽油量
     * @param stations  加油站{位置，可加油量}
     * @return 加油的次数
     */
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        final int N = stations.length;
        int i = 0;

        //当前汽车的状态{位置，剩余的汽油量}
        int curPos = 0;
        int fuelLeft = startFuel;

        //副油箱
        Queue<Integer> Q = new PriorityQueue<>((v1, v2) -> (v2 - v1));
        //从副油箱往汽车加油的次数
        int addFuelTimes = 0;

        while (curPos + fuelLeft < target) {
            //默认期望的下一站，站点设置为target
            //此时能加的汽油为0
            int pos = target;
            int fuel = 0;

            //如果在target之前有站点
            //那么更新加油站的位置，和能加到副油箱的油量
            if (i < N && stations[i][0] <= target) {
                pos = stations[i][0];
                fuel = stations[i][1];
            }

            //如果当前汽车的状态不能到达期望的下一站
            while (curPos + fuelLeft < pos) {
                //OMG，副油箱也没有油了
                if (Q.isEmpty()) {
                    return -1;
                }
                //从副油箱里拿出最大的油量加进去
                final int curMaxFuel = Q.peek();
                Q.poll();
                fuelLeft += curMaxFuel;
                addFuelTimes++; //更新加油次数
            }

            //现在可以到达期望的下一站了，需要把消耗的汽油扣掉
            final int fuelCost = pos - curPos;
            //更新当前汽车的状态
            fuelLeft -= fuelCost;
            curPos = pos;
            //将当前汽油站的汽油放到副油箱
            if (fuel > 0) {
                Q.offer(fuel);
            }
            //pass这个汽油站
            i++;
        }
        //能到达target吗？能就返回加油次数，不能就返回-1
        return curPos + fuelLeft >= target ? addFuelTimes : -1;
    }
}
