package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestSort {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Random random = new Random(5);
        for (int i = 0; i < 4; i++) {
            list.add(random.nextInt());
        }
        System.out.println("before====");
        list.forEach(e -> System.out.println(e));

        System.out.println("after====");

        list.sort((a,b) -> {
            if (a > b) return 1;
            else return -1;
        });
        list.forEach(e -> System.out.println(e));
    }
}
