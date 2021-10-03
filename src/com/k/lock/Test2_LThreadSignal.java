package com.k.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//用 Lock接口 实现线程间交替+1-1操作
//1.创建资源类 定义属性、方法
class Share {
    private int i=0;

    //创建lock
    private Lock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    //2.在资源类操作方法
    public void incr() throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while(i!=0) {
                condition.await();
            }
            //干活
            i++;
            System.out.println(Thread.currentThread().getName()+":"+i);
            //通知
            condition.signalAll();
        } finally {
            //解锁
            lock.unlock();
        }
    }

    public void decr() throws InterruptedException {
        lock.lock();
        try {
            while(i!=1) {
                condition.await();
            }
            i--;
            System.out.println(Thread.currentThread().getName()+":"+i);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

public class Test2_LThreadSignal {
    //3.创建多个线程 调用资源类方法
    public static void main(String[] args) {
        //创建资源类对象
        Share share=new Share();

        //创建多个线程
        new Thread(()->{
            for(int i=0;i<10;i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(()->{
            for(int i=0;i<10;i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(()->{
            for(int i=0;i<10;i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(()->{
            for(int i=0;i<10;i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }
}