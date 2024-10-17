package com.example.demo;

import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

public class TstLock {
    private static final Map<String, Thread> LOCK_MAP = new HashMap<>();
    private static final Map<String, List<Thread>> WAIT_QUEUE = new HashMap<>();

    public static void main(String[] args) throws IOException {
        String code = "lockCode";
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    lock(code);
                    System.out.println(code + ":" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } finally {
                    unlock(code);
                }
            }).start();


        }

        System.in.read();
    }


    protected static void lock(String code) {
        if (Objects.equals(getLock(code), Thread.currentThread())) {
            // 加锁成功
        } else {
            // 自旋等待 最长10s
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(1000);
                    if (Objects.equals(getLock(code), Thread.currentThread())) {
                        return;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            throw new RuntimeException("获取锁异常");
        }
    }

    /**
     * 释放锁
     *
     * @param code
     */
    protected static void unlock(String code) {
        synchronized (LOCK_MAP) {
            // 异常退出 不能释放 别人的锁
            WAIT_QUEUE.computeIfAbsent(code, k -> new ArrayList<>()).remove(Thread.currentThread());
            if (Objects.equals(Thread.currentThread(), LOCK_MAP.get(code))) {
                LOCK_MAP.remove(code);
                // 释放空间
                if (CollectionUtils.isEmpty(WAIT_QUEUE.get(code))) {
                    WAIT_QUEUE.remove(code);
                    System.out.println("为空 删除 list ：" + Thread.currentThread());
                }
            }
            // 异常退出 就不排队了
            WAIT_QUEUE.computeIfAbsent(code, k -> new ArrayList<>()).remove(Thread.currentThread());
            System.out.println("size: " + WAIT_QUEUE.computeIfAbsent(code, k -> new ArrayList<>()).size());
        }
    }

    private static Thread getLock(String code) {
        synchronized (LOCK_MAP) {
            // 先排队 在拿锁
            Thread thread = Thread.currentThread();
            List<Thread> list = WAIT_QUEUE.computeIfAbsent(code, k -> new ArrayList<>());
            if (list.isEmpty() || !list.contains(thread)) {
                System.out.println("加入队列：" + Thread.currentThread());
                list.add(thread);
            }
            // 先进先出
            return LOCK_MAP.computeIfAbsent(code, k -> WAIT_QUEUE.get(code).get(0));
        }
    }
}
