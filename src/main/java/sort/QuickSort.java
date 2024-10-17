package sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author dongli
 * @create 2023/6/15 14:22
 * @desc
 */

public class QuickSort {
   static Integer[] tmp = Constans.NUMBER;
    public static void main(String[] args) {
        System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
        System.out.println("--------------------------------------");

        quickSort(0, tmp.length -1);
        System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
    }

    private static void quickSort(int left, int right) {
        if (left < right) {
            int pivot  = partition(left, right);
            quickSort(left, pivot  -1);
            quickSort(pivot  + 1, right);
        }
    }

    private static int partition(int left, int right) {
        int pivot = left;
        int index = pivot + 1;

        boolean change = false;
        for (int i = index; i <= right; i ++) {
            if (tmp[i] < tmp[pivot]) {
                int temp = tmp[i];
                tmp[i] = tmp[index];
                tmp[index] = temp;
                index ++;
                change = true;
                System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
            }
        }
        if (change) {
            int temp = tmp[pivot];
            tmp[pivot] = tmp[index - 1];
            tmp[index - 1] = temp;
        }
        System.out.println(Arrays.stream(tmp).collect(Collectors.toList()));
        System.out.println("--------------------------------------");
        return index - 1;
    }
}
