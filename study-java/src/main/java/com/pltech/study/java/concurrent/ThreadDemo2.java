package com.pltech.study.java.concurrent;

/**
 * Created by Pang Li on 2020/12/18
 * 用两个线程交替输出内容，一个输出字母，一个输出数字。1A2B3C4D5E6F7G
 */
public class ThreadDemo2 {
    private static final String[] charArr = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static final int[] numArr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
            20, 21, 22, 23, 24, 25, 26};

    public static void main(String[] args) {
        Object o = new Object();
        Thread thread1 = new Thread(() -> {
            for (String c : charArr) {
                synchronized (o) {
                    // 只有拿到o这把锁才可以打印，由于先启动线程1，所以线程1先拿到这把锁
                    System.out.print(c);
                    try {
                        // 唤醒任意一个线程，让它去竞争锁
                        o.notify();
                        // 释放锁
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int n : numArr) {
                synchronized (o) {
                    // 当第一个线程释放锁后 线程2拿到锁
                    System.out.print(n);
                    try {
                        // 叫醒队列里任意一个线程去竞争锁
                        o.notify();
                        // 如果是最后一个元素就不用释放锁去排队了
                        if (n != numArr.length) {
                            // 释放锁
                            o.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
