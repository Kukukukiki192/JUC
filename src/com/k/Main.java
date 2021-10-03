package com.k;

public class Main {
    public static void main(String[] args) {
        Thread a = new Thread(() -> {
            try {
                Thread.sleep(1000);                                     //用户线程false 守护线程true
                System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
                while (true) {

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "a");
        //设置守护线程
        a.setDaemon(true);  //在start前设置
        a.start();  //a main 执行顺序不同导致a打印 可以在打印前睡几秒 (线程执行顺序不固定)
        System.out.println(Thread.currentThread().getName()+" over");
    }
}
