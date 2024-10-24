package test;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author dongli
 * @create 2024/5/11 11:03
 * @desc
 */

public class TestDate {
    public static void main(String[] args) {
//        List<String> list1 = new ArrayList<>();
//        list1.add("apple");
//        list1.add("banana");
//        list1.add("orange");
//
//        List<String> list2 = new ArrayList<>();
//        list2.add("banana");
//        list2.add("orange");
//        list2.add("watermelon");
//        list2.retainAll(list1);
//        System.out.println(String.join("/", list1));
//        System.out.println(String.join("/", list2));
//        Map<String, Integer> map = new HashMap<>();
//        map.put("a", 12);
//        map.put("b", 32423);
//        map.entrySet().removeIf(e -> Objects.equals(e.getKey(),"a"));
//        System.out.println(JSON.toJSONString(map));
//        Integer.parseInt(new BigDecimal("5.0").toString());
        List<String> list = new ArrayList<>();
        list.add("1-2");
        list.add("1-1");
        list.add("7-1");
        list.add("5-1");
        Collections.sort(list);
        System.out.println(JSON.toJSONString(list));

        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime startMonth = LocalDateTime.of(nowTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN);
        // 月末
        LocalDateTime endMonth = LocalDateTime.of(nowTime.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate(), LocalTime.MAX);
        System.out.println(startMonth);
        System.out.println(endMonth);
    }
}
