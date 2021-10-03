package com.k.sync;

//1.创建资源类 定义属性、方法
class Ticket {
    //票数
    private int number=30;
    //卖票
    public synchronized void sale() {   //synchronized自动上锁 去掉关键字 输出全是A卖出的
        if(number>0) {
            System.out.println(Thread.currentThread().getName()+"卖出"+number--+"号票");
        }
    }
}

public class Test1_SaleTicket {
    //2.创建多个线程 调用资源类方法
    public static void main(String[] args) {
        //创建资源类对象
        Ticket ticket=new Ticket();

        //创建3个线程
        new Thread(new Runnable() { //匿名内部类
            @Override
            public void run() {
                //调用资源类方法
                for(int i=0;i<40;i++) {
                    ticket.sale();
                }
            }
        }, "A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<40;i++) {
                    ticket.sale();
                }
            }
        }, "B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<40;i++) {
                    ticket.sale();
                }
            }
        }, "C").start();


    }
}
