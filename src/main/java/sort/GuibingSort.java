package sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author dongli
 * @create 2023/6/14 19:57
 * @desc
 */

public class GuibingSort {
    static  Integer[] tmp = Constans.NUMBER;
    public static void main(String[] args) {
        Integer[] tmp = Constans.NUMBER;
        System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
        System.out.println("--------------------------------------");
        int middle = tmp.length / 2 ;

        mergeList(0, middle);
        mergeList(middle, tmp.length);
        merge(0, middle, tmp.length);
        System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));

    }


    /**
     * [start, end) 左闭 右开
     * @param start
     * @param end
     * @return
     */
    private static void  mergeList(int start, int end) {
        if (end - start == 1) {
             return ;
        } else {
         int middle = (start + end) / 2 ;
            mergeList(start, middle);
            mergeList(middle, end);
            merge(start, middle, end);
        }
    }

    /**
     *
     * @param start
     * @param middle
     * @param end
     */
    private static void  merge(int start, int middle, int end) {
        int leftPosition = start;
        int rightPosition = middle;
        while (leftPosition < middle && rightPosition < end) {
            if (tmp[leftPosition] < tmp[rightPosition]) {
                int temp = tmp[leftPosition];
                tmp[leftPosition] = tmp[rightPosition];
                tmp[rightPosition] = temp;
                System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
            } else {
                leftPosition++;
            }
        }

        System.out.println("--------------------------------------");
    }

}
