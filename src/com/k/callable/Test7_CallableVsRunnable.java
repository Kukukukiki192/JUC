package com.k.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable接口创建线程
 */
class Thread1 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " come in Runnable");
    }
}

class Thread2 implements Callable {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " come in Callable");
        return 200;
    }
}

public class Test7_CallableVsRunnable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Runnable接口创建线程
        new Thread(new Thread1(), "A").start();

        //Callable接口创建线程 error->不能直接替换 Runnable,因为 Thread 类的构造方法没有 Callable
//        new Thread(new Thread2(), "B").start();

        //解决:用FutureTask替换Runnable
        //FutureTask
        FutureTask<Integer> futureTask1 = new FutureTask<>(new Thread2());

        //Lambda
        FutureTask<Integer> futureTask2 = new FutureTask<>(()->{
            System.out.println(Thread.currentThread().getName() + " come in Callable");
            return 1024;
        });

        //创建线程
        new Thread(futureTask2,"B").start();
        new Thread(futureTask1,"C").start();

//        while(!futureTask2.isDone()) {
//            System.out.println("wait..");
//        }

        //调用FutureTask的get方法
        System.out.println(futureTask2.get());  //之前做了各种计算,只计算一次
//        System.out.println(futureTask2.get());  //第2次直接返回结果
        System.out.println(futureTask1.get());

        System.out.println(Thread.currentThread().getName() + " come over");

        /** FutureTask原理 未来任务
         * 不影响主线程的情况下,单开启线程去完成其它任务,主线程需要时直接get
         */
    }
}