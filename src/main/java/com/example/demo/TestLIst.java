package com.example.demo;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestLIst {
    public static void main(String[] args) {
       /* int batch = 2;
        List<Integer>  list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(i);
        }
        System.out.println(list);
        int count = list.size() / batch;
        for (int i = 0; i <= count; i++) {
            int start = i * batch;
            int end = Math.min(list.size(), (i + 1) * batch);
            List<Integer> ids = list.subList(start, end);
            System.out.println(JSON.toJSONString(ids));
            System.out.println(ids);
        }*/

        List<Double> list = new ArrayList<>();
        list.add(1d);
        list.add(0.5);
        list.add(0.3);
        double f = 5;
        list.add(f);

        Collections.sort(list);
        System.out.println(list);

        List<Integer> list1 = new ArrayList<>();
        list1.add(1); list1.add(2);list1.add(33);
        List<Integer> list2 = new ArrayList<>();
        for (int i =0 ; i < 10; i++)list2.add(i);
        list1.removeAll(list2);
        System.out.println(JSON.toJSONString(list1));

    }
}
