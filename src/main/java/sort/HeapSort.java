package sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author dongli
 * @create 2023/6/15 15:29
 * @desc
 */

public class HeapSort {
    static Integer[] tmp = Constans.NUMBER;
    public static void main(String[] args) {
        System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
        System.out.println("--------------------------------------");

        System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
    }
}
