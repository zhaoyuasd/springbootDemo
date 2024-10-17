package com.example.demo;

import com.xxl.job.core.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TestDate {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();

        Date  d1 = calendar.getTime();
        calendar.add(Calendar.HOUR, 10);
        Date  d2=calendar.getTime();
        calendar.add(Calendar.HOUR, 10);
        Date  d3=calendar.getTime();

        System.out.println(DateUtil.formatDateTime(d1));
        System.out.println(DateUtil.formatDateTime(d2));
        System.out.println(DateUtil.formatDateTime(d3));

        List<Date> raw = new ArrayList<>();
        raw.add(d2);
        raw.add(d3);
        raw.add(d1);
        System.out.println("before:");
        raw.forEach(e -> System.out.println(DateUtil.formatDateTime(e)));

        System.out.println("after asc:");
        raw.sort(Date::compareTo);
        raw.forEach(e -> System.out.println(DateUtil.formatDateTime(e)));

        System.out.println("after desc:");
        raw.sort((t1, t2) -> t2.compareTo(t1));
        raw.forEach(e -> System.out.println(DateUtil.formatDateTime(e)));
        System.out.println("============================");
        while (!raw.isEmpty()) {
            System.out.println(DateUtil.formatDateTime(raw.remove(0)));
        }


    }
}
