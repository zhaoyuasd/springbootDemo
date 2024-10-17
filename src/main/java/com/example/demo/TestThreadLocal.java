package com.example.demo;

public class TestThreadLocal {
    private static final ThreadLocal<Integer> LOCAL = new ThreadLocal<>();
    private static final InheritableThreadLocal<Integer> LOCAL_CHILD = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        LOCAL.set(23);
        doOther();
    }

    private static void doOther() {
        LOCAL_CHILD.set(LOCAL.get());
        new Thread(() -> {
            System.out.println("parentValue :" + LOCAL_CHILD.get());
        }).start();
    }
}
