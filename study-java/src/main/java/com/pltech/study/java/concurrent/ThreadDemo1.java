package com.pltech.study.java.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by Pang Li on 2020/12/18
 * 用两个线程交替输出内容，一个输出字母，一个输出数字。1A2B3C4D5E6F7G
 */
public class ThreadDemo1 {
    private static final String[] charArr = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N",
            "O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private static final int[] numArr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
            20, 21, 22, 23, 24, 25, 26};
    private static Thread thread1 = null;
    private static Thread thread2 = null;

    public static void main(String[] args) {

        thread1 = new Thread(()->{
            for (String c : charArr) {
                System.out.print(c);
                // 打印完让线程2执行
                LockSupport.unpark(thread2);
                // 线程1等待
                LockSupport.park(thread1);
            }
        });

        thread2 = new Thread(()->{
            for (int n : numArr) {
                // 上来线程2先等待
                LockSupport.park(thread2);
                System.out.print(n);
                // 输出完唤醒线程1，让线程1执行
                LockSupport.unpark(thread1);
            }
        });

        thread1.start();
        thread2.start();
    }
}
