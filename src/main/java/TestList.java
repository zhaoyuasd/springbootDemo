import com.alibaba.fastjson.JSON;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dongli
 * @create 2024/4/22 9:51
 * @desc
 */

public class TestList {
    public static void main(String[] args) {
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            list.add(i);
//        }
//
//        list.add(3);
//        list.add(-9);
//        list.sort(Integer::compareTo);
//        System.out.println(JSON.toJSONString(list));
//        List<Integer> nwList = getNewList(list);
//        System.out.println(JSON.toJSONString(list));
//        for (Integer i : list) {
//            System.out.println(i);
//        }
//        System.out.println(JSON.toJSONString(nwList));

        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime startMonth = LocalDateTime.of(nowTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN);
        // 月末
        LocalDateTime endMonth = LocalDateTime.of(nowTime.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate(), LocalTime.MAX);
        System.out.println(startMonth);
        System.out.println(endMonth);
    }

    private static List<Integer> getNewList(List<Integer> list) {
        list = list.stream().filter(e -> e > 3).collect(Collectors.toList());
        return list;
    }
}
