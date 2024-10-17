package sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author dongli
 * @create 2023/6/14 17:07
 * @desc
 */

public class InsertSort {
    public static void main(String[] args) {
        Integer[] tmp = Constans.NUMBER;
        System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
        System.out.println("--------------------------------------");
        int step = tmp.length / 2;

        for (int i = 1; i < tmp.length; i ++) {
            int current = i;
            for (int j = i -1; j >= 0; j --) {
                if (tmp[current] > tmp[j]) {
                    int temp = tmp[j];
                    tmp[j] = tmp[current];
                    tmp[current] = temp;
                    current = j;
                    System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
                } else {
                    break;
                }
            }
            System.out.println("--------------------------------------");
        }

        System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
    }
}
