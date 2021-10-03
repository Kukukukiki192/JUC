package com.k.lock;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

//List HashSet HashMap 线程不安全
public class Test3_CollectionUnsafe {
    public static void main(String[] args) {
        //ArrayList线程不安全
//        List<String> list=new ArrayList<>();
        //Vector解决
//        List<String> list=new Vector<>();
        //Collections解决
//        List<String> list= Collections.synchronizedList(new ArrayList<>());
        //CopyOnWriteArrayList解决
        List<String> list=new CopyOnWriteArrayList<>();

        //HashSet线程不安全
//        Set<String> set=new HashSet<>();
        //CopyOnWriteArraySet解决
        Set<String> set=new CopyOnWriteArraySet<>();

        //HashMap线程不安全
//        Map<String,String> map=new HashMap<>();
        //Hashtable解决
//        Map<String,String> map=new Hashtable<>();
        //ConcurrentHashMap解决
        Map<String,String> map=new ConcurrentHashMap<>();

        for(int i=0;i<30;i++) {
            String key=String.valueOf(i);
            new Thread(()->{
                //向集合添加内容
//                list.add(UUID.randomUUID().toString().substring(0,8));
//                set.add(UUID.randomUUID().toString().substring(0,8));
                map.put(key,UUID.randomUUID().toString().substring(0,8));
                //从集合获取内容
//                System.out.println(list);
//                System.out.println(set);
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}
