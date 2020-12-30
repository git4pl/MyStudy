package com.pltech.study.java.concurrent;

/**
 * Created by Pang Li on 2020/12/18
 * 用两个线程交替输出内容，一个输出字母，一个输出数字。1A2B3C4D5E6F7G
 */
public class ThreadDemo3 {
    private static final String[] charArr = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static final int[] numArr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
            20, 21, 22, 23, 24, 25, 26};


    // 定义两个线程开关
    enum Run {T1, T2}

    // 定义线程1先执行
    // volatile让线程保持可见性
    static volatile Run run = Run.T1;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            for (String c : charArr) {
                // 如果是线程2运行，则自旋等待
                while (run == Run.T2) {
                }
                System.out.print(c);

                // 让线程2运行
                run = Run.T2;
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int n : numArr) {
                // 如果是线程1执行，空转等待
                while (run == Run.T1) {
                }
                System.out.print(n);
                // 让线程1运行
                run = Run.T1;
            }
        });

        thread1.start();
        thread2.start();
    }
}
