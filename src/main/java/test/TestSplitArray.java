package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author dongli
 * @create 2025/10/24 14:20
 * @desc
 */

public class TestSplitArray {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
       /* Random random = new Random();

        for (int i = 0; i < 20; i ++) {
            list.add(random.nextInt(20));
        }*/
        list.add(3);
        list.add(1);
        list.add(1);
        list.add(4);
        list.add(7);
        list.add(2);


        List<Integer> list2 = new ArrayList<>(list);
        System.out.println(JSON.toJSONString(list));
        List<Integer[]> result = new ArrayList<>();
        Integer size = list.size();
        int spiltOrderCount = 3;
        boolean needBreak = false;

        outer:
        if (!needBreak) {
            for (int length = 0; length < size; length++) {
                int currentLength = 0;
                int fillLength = length + 1;
                for (int f = 0; f < fillLength; f++) {
                    for (int j = f; j < size; j++) {
                        Integer[] matchCountOrderItem = new Integer[fillLength];
                        Integer[] array = list.toArray(new Integer[0]);
                        fillAndCheck(currentLength, j, fillLength, array, matchCountOrderItem, result, spiltOrderCount, list);
                        needBreak = list.stream().mapToInt(e -> e).sum() < spiltOrderCount ;
                        if (needBreak) {
                            break outer;
                        }
                    }
                }
            }
        }
        String json = JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        System.out.println(json);
        System.out.println(JSON.toJSONString(list));
        System.out.println("over");
        List<Integer> match = result.stream().flatMap(Arrays::stream).collect(Collectors.toList());
        match.addAll(list);
        Collections.sort(match);
        Collections.sort(list2);

        System.out.println("====================");
        System.out.println(JSON.toJSONString(match));
        System.out.println(JSON.toJSONString(list2));
    }

    private static void fillAndCheck(int currentLength, int next, int fillOrderItemCount, Integer[] array, Integer[] matchCountOrderItem, List<Integer[]> result, int spiltOrderCount,  List<Integer> list) {
        if (next >= array.length) {
            return;
        }
        matchCountOrderItem[currentLength] = array[next];
        currentLength ++;
        next++;

        if (currentLength >= fillOrderItemCount) {
            if(checkMatch(matchCountOrderItem, spiltOrderCount)) {
                result.add(matchCountOrderItem);
                for (Integer i : matchCountOrderItem) {
                    list.remove(i);
                }
                return;
            } else {
                currentLength --;
            }
        }
        if (currentLength <= 0) {
            return;
        }
        fillAndCheck(currentLength, next, fillOrderItemCount, array, matchCountOrderItem, result, spiltOrderCount, list);
    }

    private static boolean checkMatch(Integer[] matchCountOrderItem, int spiltOrderCount) {
        return Arrays.stream(matchCountOrderItem).mapToInt(e -> e).sum() % spiltOrderCount == 0;
    }
}
