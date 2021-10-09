package com.k.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏CyclicBarrier +1
 * 场景: 集齐7颗龙珠就可以召唤神龙
 */
public class Test9_CyclicBarrier {
    //1.创建固定值
    private static final int NUM = 7;

    public static void main(String[] args) {
        //2.创建CyclicBarrier对象   设置固定值、达到值后要做的事
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUM, ()->{
            System.out.println("集齐7颗龙珠就可以召唤神龙");
        });

        for(int i=1;i<=7;i++) {
            new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName() + "星龙被收集");
                    //3.等待 +1 计数值+到7才会召唤神龙
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}