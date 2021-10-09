package com.k.juc;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号灯Semaphore
 * 场景: 6辆车抢3个停车位
 */
public class Test10_Semaphore {
    public static void main(String[] args) {
        //创建Semaphore对象,设置许可数量
        Semaphore semaphore = new Semaphore(3);

        //模拟6辆车
        for(int i=1;i<=6;i++) {
            new Thread(()->{
                try {
                    //抢车位
                    semaphore.acquire();

                    System.out.println(Thread.currentThread().getName() + "抢到车位");

                    //设置随机停车时间(5s内)
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));

                    System.out.println(Thread.currentThread().getName() + "离开车位---------");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //释放车位
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
