package sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author dongli
 * @create 2023/6/15 13:48
 * @desc
 */

public class GuibingAollcateSort {

    static  Integer[] tmp = Constans.NUMBER;
    public static void main(String[] args) {
        Integer[] tmp = Constans.NUMBER;
        System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
        System.out.println("--------------------------------------");
        int middle = tmp.length / 2 ;
        Integer[] result =  merge(mergeList(Arrays.copyOfRange(tmp, 0, middle)),  mergeList(Arrays.copyOfRange(tmp, middle, tmp.length)));
        System.out.println(Arrays.stream(result).collect(Collectors.toList()));

    }


    /**
     * [start, end) 左闭 右开
     * @param start
     * @param end
     * @return
     */
    private static Integer[]  mergeList(Integer[] list) {
        if (list.length == 1) {
            return list;
        } else {
            int middle = list.length / 2 ;
            return merge(mergeList(Arrays.copyOfRange(list, 0, middle)),  mergeList(Arrays.copyOfRange(list, middle, list.length)));
        }
    }

    /**
     *
     * @param start
     * @param middle
     * @param end
     */
    private static Integer[]  merge(Integer[] left, Integer[] right) {
        Integer[] result = new Integer[left.length + right.length];
        int leftPosition = 0;
        int rightPosition = 0;
        int index = 0;
        while (leftPosition < left.length && rightPosition < right.length) {
            if (left[leftPosition] > right[rightPosition] ) {
                result[index++] = right[rightPosition];
                rightPosition ++;
            } else {
                result[index++] = left[leftPosition];
                leftPosition ++;
            }
        }

        if (leftPosition <  left.length) {
            for (; leftPosition < left.length; leftPosition++) {
                result[index++] = left[leftPosition];
            }
        }

        if (rightPosition < right.length) {
            for (; rightPosition < right.length; rightPosition ++) {
                result[index++] = right[rightPosition];
            }
        }
        return result;
    }

}
