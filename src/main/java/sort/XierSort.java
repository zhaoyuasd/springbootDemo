package sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author dongli
 * @create 2023/6/14 18:35
 * @desc
 */

public class XierSort {
    public static void main(String[] args) {
        Integer[] tmp = Constans.NUMBER;
        System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
        System.out.println("--------------------------------------");
        /* int step = tmp.length / 2;

        for (int i = step; i >= 1; i = i / 2) {
                 for (int k = i; k < tmp.length ; k = k + i) {
                    int current = k;
                     for (int t = k - i; t >= 0; t = t - i) {
                         if (tmp[t] < tmp[current]) {
                             int temp = tmp[t];
                             tmp[t] = tmp[current];
                             tmp[current] = temp;
                             current = t;
                             System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
                         } else {
                             break;
                         }

                     }
                     System.out.println("--------------------------------------");
                 }
        }*/

        for (int step = tmp.length / 2; step >=1 ; step /=2) {
            for (int i = 0; i < step; i++) {
                for (int t = i + step; t < tmp.length; t = t + step) {
                     int current = t;
                     for (int k = t - step; k >= 0; k = k - step) {
                         if (tmp[k] < tmp[current]) {
                             int temp = tmp[k];
                             tmp[k] = tmp[current];
                             tmp[current] = temp;
                             current = k;
                             System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
                         } else {
                             break;
                         }
                     }
                    System.out.println("--------------------------------------");
                }
            }
        }
    }
}
