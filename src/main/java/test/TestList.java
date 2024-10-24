package test;

import com.alibaba.fastjson.JSON;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author dongli
 * @create 2024/4/22 9:51
 * @desc
 */

public class TestList {
    public static void main(String[] args) {

       // test1();
        testEquals();


    }

    private static void testEquals() {
        List<Integer> intlist1 = new ArrayList<>();
        intlist1.add(1); intlist1.add(2);
        List<Integer> intlist2 = new ArrayList<>();
        intlist2.add(2);intlist2.add(1);
        System.out.println("intlist :" + Objects.equals(intlist1, intlist2));  //false

        List<String> strlist1 = new ArrayList<>();
        strlist1.add("1"); strlist1.add("2");
        List<String> strist2 = new ArrayList<>();
        strist2.add("2");strist2.add("1");
        System.out.println("strlist :" + Objects.equals(strlist1, strist2));//false

    }

    private static void test1() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        list.add(3);
        list.add(-9);
        list.sort(Integer::compareTo);
        System.out.println(JSON.toJSONString(list));
        List<Integer> nwList = getNewList(list);
        System.out.println(JSON.toJSONString(list));
        for (Integer i : list) {
            System.out.println(i);
        }
        System.out.println(JSON.toJSONString(nwList));
    }


    private static List<Integer> getNewList(List<Integer> list) {
        list = list.stream().filter(e -> e > 3).collect(Collectors.toList());
        return list;
    }
}
