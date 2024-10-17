package com.example.demo;

/**
 * @author dongli
 * @create 2023/6/25 14:50
 * @desc
 */

public class TestStaticThis {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            System.out.println(new StaticThis().hashCode());
        }
    }
}

class StaticThis {
    private static StaticThis INSTANCE;
    StaticThis() {
        INSTANCE = this;
    }
}