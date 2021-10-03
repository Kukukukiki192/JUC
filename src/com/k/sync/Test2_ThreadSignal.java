package com.k.sync;

//用 synchronized关键字 实现线程间交替+1-1操作
//1.创建资源类 定义属性、方法
class Share {
    private int i=0;

    //2.在资源类操作方法(在方法中 判断 干活 通知)
    public synchronized void incr() throws InterruptedException {
        //判断
        while(i!=0) {
            this.wait();    //不满足干活条件->在被通知前<等待> 释放锁
        }
        //干活
        i++;
        System.out.println(Thread.currentThread().getName()+":"+i);
        //通知其它线程
        this.notifyAll();
    }

    public synchronized void decr() throws InterruptedException {
        //判断
        while(i!=1) {
            this.wait();    //if的wait:在哪里睡,在哪里醒 被调用时唤醒->虚假唤醒 解决:条件放到while中
        }
        //干活
        i--;
        System.out.println(Thread.currentThread().getName()+":"+i);
        //通知
        this.notifyAll();
    }
}

public class Test2_ThreadSignal {
    //3.创建多个线程 调用资源类方法
    public static void main(String[] args) {
        //创建资源类对象
        Share share=new Share();

        //创建2个线程 交替实现+1-1
        //若是多个线程,等待条件放在if中:+的锁释放后又被+的线程抢到了,会+到>1 -的操作也会-到<0
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