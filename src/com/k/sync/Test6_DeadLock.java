package com.k.sync;

import java.util.concurrent.TimeUnit;

/**
 * 演示死锁
 */
public class Test6_DeadLock {
    static Object a=new Object();
    static Object b=new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (a) {
                System.out.println(Thread.currentThread().getName()+"持有锁a,试图获取锁b");
                //睡眠1s让线程创建确定,使死锁效果更明显
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    System.out.println(Thread.currentThread().getName()+"获取锁b");
                }
            }
        },"A").start();

        new Thread(()->{
            synchronized (b) {
                System.out.println(Thread.currentThread().getName()+"持有锁b,试图获取锁a");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {
                    System.out.println(Thread.currentThread().getName()+"获取锁a");
                }
            }
        },"B").start();
    }
}
