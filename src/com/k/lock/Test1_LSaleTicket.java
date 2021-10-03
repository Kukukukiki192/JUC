package com.k.lock;

import java.util.concurrent.locks.ReentrantLock;

//1.创建资源类 定义属性、方法
class Ticket {
    //票数
    private int number=30;
    //创建可重入锁
    private final ReentrantLock lock = new ReentrantLock();
    //卖票
    public void sale() {   //用ReentrantLock手动上锁
        //加锁
        lock.lock();
        try{
            if(number>0) {
                System.out.println(Thread.currentThread().getName()+"卖出"+number--+"号票");
            }
        } finally { //Lock发生异常时,若不主动释放锁,会死锁->finally保证不管有无异常都会释放锁
            //解锁
            lock.unlock();
        }
    }
}

public class Test1_LSaleTicket {
    //2.创建多个线程 调用资源类方法
    public static void main(String[] args) {
        //创建资源类对象
        Ticket ticket = new Ticket();

        //创建3个线程
        new Thread(() -> { //Lambda表达式
            //调用资源类方法
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "A").start();    //线程调用start后可能马上创建(OS空闲),也可能等会儿创建(OS忙) 取决于OS

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "C").start();




    }
}
