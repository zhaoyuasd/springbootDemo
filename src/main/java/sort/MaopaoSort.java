package sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author dongli
 * @create 2023/6/14 16:38
 * @desc
 */

public class MaopaoSort {
    public static void main(String[] args) {
        Integer[] tmp = Constans.NUMBER;
        System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
        System.out.println("--------------------------------------");
        for (int i = 0; i < tmp.length; i++) {
            for (int j = i +1; j < tmp.length; j++) {
                if (tmp[i] > tmp[j]) {
                    int temp = tmp[i];
                    tmp[i] = tmp[j];
                    tmp[j] = temp;
                    System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
                }
            }
            System.out.println("--------------------------------------");
        }
        System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
    }
}
