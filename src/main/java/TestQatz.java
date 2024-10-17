import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dongli
 * @create 2023/11/9 10:35
 * @desc
 */

public class TestQatz {
    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(getStockCombination(1)));
    }

    private static List<List<Integer>> getStockCombination(int num) {
        Integer[] array = new Integer[num];
        for (int i = 0; i < num; i++) {
            array[i] = i + 1;
        }
        List<List<Integer>> auto = new ArrayList<>();
        int binNum = 1 << num;

        for (int i = 1; i < binNum; i++) {
            List<Integer> element = new ArrayList<>();
            for (int j = 0; j < num; j++) {
                if (i << (31 - j) >> 31 == -1) {
                    element.add(array[j]);
                }
            }
            auto.add(element);
        }

        Map<Integer, List<List<Integer>>> collect = auto.stream().filter(a -> a.size() > 1).collect(Collectors.groupingBy(List::size));
        List<List<Integer>> result = new ArrayList<>();
        for (Map.Entry<Integer, List<List<Integer>>> entry : collect.entrySet()) {
            List<List<Integer>> ele = new ArrayList<>();
            sort(ele, entry.getValue(), entry.getKey(), 0);
            result.addAll(ele);
        }
        return result;
    }

    private static void sort(List<List<Integer>> result, List<List<Integer>> origin, int len, int index) {
        origin.sort(Comparator.comparing(o -> o.get(index)));

        if (index == len - 1) {
            result.addAll(origin);
        } else {
            Map<Integer, List<List<Integer>>> collect = origin.stream().collect(Collectors.groupingBy(l -> l.get(index)));
            for (Map.Entry<Integer, List<List<Integer>>> entry : collect.entrySet()) {
                int next = index + 1;
                if (next < len) {
                    sort(result, entry.getValue(), len, next);
                }
            }

        }
    }
}
