package com.k.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//synchronized、Lock 演示可重入锁
public class Test5_ReentraintLock {
    //同步方法演示可重入锁
    public synchronized void add() {
        add();  //因为是可重入锁，所以会递归调用add()；若是不可重入锁. 就会等待this对象释放锁，这段代码会死锁
    }

    public static void main(String[] args) {
//        new Test5_ReentraintLock().add();   //循环递归调用->最后栈溢出

        //同步代码块演示可重入锁
        Object o=new Object();
        new Thread(()->{
            synchronized (o) {
                System.out.println(Thread.currentThread().getName()+":外层");
                synchronized (o) {
                    System.out.println(Thread.currentThread().getName()+":中层");
                    synchronized (o) {
                        System.out.println(Thread.currentThread().getName()+":内层");
                    }
                }
            }
        },"A").start();

        //Lock演示可重入锁
        Lock lock=new ReentrantLock();
        new Thread(()->{
            try {
                lock.lock();    //上锁
                System.out.println(Thread.currentThread().getName()+":外层");
                try {
                    lock.lock();    //上锁
                    System.out.println(Thread.currentThread().getName()+":内层");
                } finally {
                    lock.unlock();  //解锁
                }
            } finally {
                lock.unlock();  //解锁
            }
        },"B").start();

        new Thread(()->{
            lock.lock();    //若是另一个线程不解锁 这个线程就得不到锁 一直等待不能执行
            System.out.println(Thread.currentThread().getName());
            lock.unlock();
        },"C").start();
    }
}
