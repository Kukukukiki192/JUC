package com.k.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁降级
 */
public class Test12_DemoteRWLock {
    public static void main(String[] args) {
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();   //可重入读写锁对象
        ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();   //读锁
        ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();//写锁

        //锁降级 写锁--降级-->读锁
//        //2.获取读锁
//        readLock.lock();
//        System.out.println("读..");

        //1.获取写锁
        writeLock.lock();
        System.out.println("写..");

        //2.获取读锁
        readLock.lock();
        System.out.println("读..");

        //3.释放写锁
        writeLock.unlock();

        //4.释放读锁
        readLock.unlock();
    }
}
