package com.k.readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 模拟缓存情景
 */

//创建资源类 定义属性、方法
class MyCache {
    //创建 map 集合
    private volatile Map<String,Object> map = new HashMap<>();
    //volatile强制线程到主存而非自己的高速缓存中获取数据 因为数据经常被修改,不断发生变化

    //创建读写锁对象
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    //存数据
    public void put(String key,Object value) {
        //添加写锁
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "写.." + key);
            //写一会儿
            TimeUnit.MICROSECONDS.sleep(300); //睡眠300ms
            map.put(key,value);
            System.out.println(Thread.currentThread().getName() + "写完" + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放写锁
            rwLock.writeLock().unlock();
        }
    }

    //取数据
    public Object get(String key) {
        //添加读锁
        rwLock.readLock().lock();
        Object value = null;
        try {
            System.out.println(Thread.currentThread().getName() + "读.." + key);
            //读一会儿
            TimeUnit.MICROSECONDS.sleep(300);
            value = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读完" + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放读锁
            rwLock.readLock().unlock();
        }
        return value;
    }
}

//创建多个线程 调用资源类方法
public class Test11_ReadWriteLock {
    public static void main(String[] args) throws InterruptedException {
        MyCache myCache = new MyCache();

        //创建线程存数据
        for(int i=1;i<=5;i++) {
            final int num = i;
            new Thread(()->{
//                myCache.put(String.valueOf(i),i);//error 只能访问常量
                myCache.put(num+"",num);
            },String.valueOf(i)).start();
        }

//        TimeUnit.MICROSECONDS.sleep(300);	//使先写再读的效果更明显 不加这行运行结果也一样

        //创建线程取数据
        for(int i=1;i<=5;i++) {
            final int num = i;
            new Thread(()->{
                myCache.get(num+"");
            },String.valueOf(i)).start();
        }
    }
}