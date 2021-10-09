package com.k.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 减少计数CountDownLatch -1
 * 场景：6个同学陆续离开教室后班长才可以关门
 */
public class Test8_CountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        //1.创建CountDownLatch对象,设置初始值
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for(int i=1;i<=6;i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "号同学离开教室");
                //2.计数 -1
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        //3.等待 计数值减到0才会执行之后代码
        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + "班长锁门走人");
    }
}