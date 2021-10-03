package com.k.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//用 Lock接口 实现 线程间定制化通信
//1.创建资源类 定义属性、方法
class ShareResource {
    //定义标志位 A-1 B-2 C-3
    private int flag=1;

    //创建lock
    private Lock lock=new ReentrantLock();
    private Condition c1=lock.newCondition();
    private Condition c2=lock.newCondition();
    private Condition c3=lock.newCondition();

    //2.在资源类操作方法
    public void print5(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while(flag!=1) {
                c1.await();
            }
            //干活
            for(int i=1;i<=5;i++) {
                System.out.println("第"+loop+"轮 "+Thread.currentThread().getName()+":"+i);
            }
            flag=2;
            //通知B
            c2.signal();
        } finally {
            //解锁
            lock.unlock();
        }
    }

    public void print10(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while(flag!=2) {
                c2.await();
            }
            //干活
            for(int i=1;i<=10;i++) {
                System.out.println("第"+loop+"轮 "+Thread.currentThread().getName()+":"+i);
            }
            flag=3;
            //通知C
            c3.signal();
        } finally {
            //解锁
            lock.unlock();
        }
    }

    public void print15(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while(flag!=3) {
                c3.await();
            }
            //干活
            for(int i=1;i<=15;i++) {
                System.out.println("第"+loop+"轮 "+Thread.currentThread().getName()+":"+i);
            }
            flag=1;
            //通知A
            c1.signal();
        } finally {
            //解锁
            lock.unlock();
        }
    }
}

public class Test2_CustomizedThreadSignal {
    //3.创建多个线程 调用资源类方法
    public static void main(String[] args) {
        //创建资源类对象
        ShareResource resource=new ShareResource();

        //创建多个线程
        new Thread(()->{
            for(int i=1;i<=10;i++) {
                try {
                    resource.print5(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(()->{
            for(int i=1;i<=10;i++) {
                try {
                    resource.print10(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(()->{
            for(int i=1;i<=10;i++) {
                try {
                    resource.print15(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();
    }
}
