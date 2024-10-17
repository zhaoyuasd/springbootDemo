package com.example.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0 ; i < 10; i++) {
            new Thread(() -> {
                countDownLatch.countDown();
                reentrantLock.tryLock();
                try {
                    System.out.println( reentrantLock.tryLock(5, TimeUnit.SECONDS) +" this:" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println(reentrantLock.getQueueLength());
    }
}
