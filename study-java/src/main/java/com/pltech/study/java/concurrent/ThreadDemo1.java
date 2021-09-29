package com.pltech.study.java.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by Pang Li on 2020/12/18
 * 用两个线程交替输出内容，一个输出字母，一个输出数字。1A2B3C4D5E6F7G
 */
public class ThreadDemo1 {
    private static final String[] charArr = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static final int[] numArr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
            20, 21, 22, 23, 24, 25, 26};
    private static Thread threadChar = null;
    private static Thread threadNum = null;
    private static Thread thread1 = null;
    private static Thread thread2 = null;
    private static Thread thread3 = null;
    private static final AtomicInteger num = new AtomicInteger(1);

    public static void main(String[] args) {

        threadChar = new Thread(() -> {
            for (String c : charArr) {
                System.out.print(c);
                // 打印完让打印数字的线程执行
                LockSupport.unpark(threadNum);
                // 打印字符的线程等待
                LockSupport.park(threadChar);
            }
        });

        threadNum = new Thread(() -> {
            for (int n : numArr) {
                // 上来打印数字的线程先等待
                LockSupport.park(threadNum);
                System.out.print(n);
                // 输出完唤醒打印字符的线程，让其执行
                LockSupport.unpark(threadChar);
            }
        });

        thread1 = new Thread(() -> {
            while (true) {
                if (num.get() > 97) {
                    return;
                }
                System.out.println("thread1: " + num.getAndIncrement());
                // 打印完让线程2执行
                LockSupport.unpark(thread2);
                // 线程1等待
                LockSupport.park(thread1);
            }
        });

        thread2 = new Thread(() -> {
            while (true) {
                if (num.get() > 98) {
                    return;
                }
                // 上来线程2先等待
                LockSupport.park(thread2);
                System.out.println("thread2: " + num.getAndIncrement());
                // 输出完唤醒线程3，让线程3执行
                LockSupport.unpark(thread3);
            }
        });

        thread3 = new Thread(() -> {
            while (true) {
                if (num.get() > 99) {
                    return;
                }
                // 上来线程3先等待
                LockSupport.park(thread3);
                System.out.println("thread3: " + num.getAndIncrement());
                // 输出完唤醒线程1，让线程1执行
                LockSupport.unpark(thread1);
            }
        });

        //threadNum.start();
        //threadChar.start();
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
