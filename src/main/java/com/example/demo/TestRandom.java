package com.example.demo;

import java.util.Random;

/**
 * @author dongli
 * @create 2023/5/18 16:44
 * @desc
 */

public class TestRandom {
    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 5; i++)
            System.out.println(random.nextInt(1));
    }
}
