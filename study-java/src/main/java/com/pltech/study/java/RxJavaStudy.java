package com.pltech.study.java;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Pang Li on 2021/1/14
 */
public class RxJavaStudy {
    private void test() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 720; i++) {   //无限循环发事件
                    subscriber.onNext(i);
                    System.out.println("put integer " + i + ", and the thread is: " + Thread.currentThread().getName());
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                //.sample(1, TimeUnit.MICROSECONDS)
                .onBackpressureDrop(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("dropped integer " + integer);
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("get Integer is " + integer + ", and the thread is: " + Thread.currentThread().getName());
                    }
                });
    }

    private void test1() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 128; i++) {
                    subscriber.onNext(i);
                    System.out.println("create call integer is: " + i + ", at time: " + System.currentTimeMillis() + ", and the thread is: " + Thread.currentThread().getName());
                    if (i % 23 == 0) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                //.onBackpressureLatest()
                //.sample(3, TimeUnit.MILLISECONDS)
                .throttleFirst(1, TimeUnit.MILLISECONDS)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onStart() {
                        //request(1);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext integer is: " + integer + ", at time: " + System.currentTimeMillis() + ", and the thread is: " + Thread.currentThread().getName());
                        //request(3);
                        //System.out.println("onNext after request");
                    }
                });
    }

    private void test2() {
        Observable.interval(1, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .sample(100, TimeUnit.MILLISECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("get integer: " + aLong + ", at time: " + System.currentTimeMillis() + ", and the thread is: " + Thread.currentThread().getName());
                    }
                });
    }

    private void test3() {
        Observable.range(1, 10000)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onStart() {
                        request(10);
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("get integer: " + integer + ", at time: " + System.currentTimeMillis() + ", and the thread is: " + Thread.currentThread().getName());
                        request(3);
                    }
                });
    }

    public static void main(String[] args) {
        RxJavaStudy rxTest = new RxJavaStudy();
        rxTest.test2();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
