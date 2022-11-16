package org.example;

import java.util.Map;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Map<Integer, String> treeMap = new ThreadSafeTreeMap<>();
        ConcurrentHashMap<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();

        Thread th1 = new Thread(() -> {
            for(int i = 0; i < 10; i++){
                treeMap.put(i, "treeMap-data-" + i);
                concurrentHashMap.put(i, "hashMap-data-" + i);
            }
        });

        Thread th2 = new Thread(() -> {
            for(int i = 10; i < 20; i++){
                treeMap.put(i, "treeMap-data-" + i);
                concurrentHashMap.put(i, "hashMap-data-" + i);
            }
        });

        Thread th3 = new Thread(()->{
            for(int i = 0; i < 10; i++){
                System.out.println("TreeMap data: " + treeMap.get(i));
            }
        });

        Thread th4 = new Thread(()->{
            for(int i = 10; i < 20; i++){
                System.out.println("TreeMap data: " + treeMap.get(i));
            }
        });

        th1.start();
        th2.start();
        Thread.sleep(4000);
        th3.start();
        th4.start();

        th1.join();
        th2.join();
        th3.join();
        th4.join();

        System.out.println(treeMap);
        System.out.println("TreeMap size: " + treeMap.size());
        System.out.println(concurrentHashMap);
        System.out.println("ConcurrentHashMap size: " + concurrentHashMap.size());
    }
}