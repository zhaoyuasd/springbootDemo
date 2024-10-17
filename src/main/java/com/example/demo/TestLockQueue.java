package com.example.demo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class TestLockQueue {
    private static final ConcurrentHashMap<String, ReentrantLock> LOCK_MAP = new ConcurrentHashMap<>();
    public static void main(String[] args) {
       String lockKey = "lock";
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                ReentrantLock lock = LOCK_MAP.computeIfAbsent(lockKey, k -> new ReentrantLock());
                System.out.println(lock.hashCode());
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " own lock queueSize:" + lock.getQueueLength() + " 锁是否存在：" + (LOCK_MAP.get(lockKey) != null));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lock.unlock();
                if (lock.getQueueLength() == 0) {
                    System.out.println(Thread.currentThread().getName() + " 删除锁：" + lock.hashCode());
                    LOCK_MAP.remove(lockKey);
                }
            }).start();
        }
    }
}
