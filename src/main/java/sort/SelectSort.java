package sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author dongli
 * @create 2023/6/14 16:59
 * @desc
 */

public class SelectSort {
    public static void main(String[] args) {
        Integer[] tmp = Constans.NUMBER;
        System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
        System.out.println("--------------------------------------");

        for (int i = 0; i < tmp.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < tmp.length; j++) {
                if (tmp[minIndex] > tmp[j]) {
                    minIndex = j;
                }
            }
            int temp = tmp[i];
            tmp[i] = tmp[minIndex];
            tmp[minIndex] = temp;
        }

        System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
    }
}
