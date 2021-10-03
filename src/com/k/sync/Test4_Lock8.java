package com.k.sync;

import java.util.concurrent.TimeUnit;

class Phone {
    public static synchronized void sendSMS() throws Exception {
//    public synchronized void sendSMS() throws Exception {
        //停留 4 秒
        TimeUnit.SECONDS.sleep(4);
        System.out.println("------sendSMS");
    }
//    public static synchronized void sendEmail() throws Exception {
    public synchronized void sendEmail() throws Exception {
        System.out.println("------sendEmail");
    }
    public void getHello() {
        System.out.println("------getHello");
    }
}
/**
 * @Description: 8 锁 *
1 标准访问(2个同步方法)，先打印短信还是邮件
------sendSMS
------sendEmail             /锁的都是phone
2 停 4 秒在短信方法内，先打印短信还是邮件
(4s后打印)
------sendSMS
------sendEmail             /锁的都是phone
3 新增普通的 hello 方法，先打印短信还是 hello
------getHello              /没加锁 与锁无关
------sendSMS (4s后打印)     /锁的是phone
4 现在有两部手机，先打印短信还是邮件
------sendEmail             /锁的是phone1
------sendSMS (4s后打印)     /锁的是phone
5 两个静态同步方法，1 部手机，先打印短信还是邮件
(4s后打印)
------sendSMS
------sendEmail             /锁的都是Phone
6 两个静态同步方法，2 部手机，先打印短信还是邮件
(4s后打印)
------sendSMS
------sendEmail             /锁的都是Phone
7 1 个静态同步方法(SMS),1 个普通同步方法(Email)，1 部手机，先打印短信还是邮件
------sendEmail             /锁的是phone1
------sendSMS (4s后打印)     /锁的是Phone
8 1 个静态同步方法(SMS),1 个普通同步方法(Email)，2 部手机，先打印短信还是邮件
------sendEmail             /锁的是phone1
------sendSMS (4s后打印)     /锁的是Phone                                  **/
public class Test4_Lock8 {
    public static void main(String[] args) throws Exception {
        Phone phone=new Phone();
        Phone phone1=new Phone();

        new Thread(()->{
            try{
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"A").start();

        Thread.sleep(100);  //线程什么时候创建不确定,所以在两线程中间睡眠一下,使效果更明显

        new Thread(()->{
            try{
//                phone.getHello();
//                phone.sendEmail();
                phone1.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"B").start();
    }
}
