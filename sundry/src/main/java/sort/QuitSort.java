package sort;

import java.util.Random;

/**
 * Created by Administrator on 2015/5/14.
 */
public class QuitSort {

    public static void main(String[] args) {
        Random r = new Random();
        int[] array = new int[10];
        for (int i = 0; i < 10; i++) {
            array[i] = r.nextInt(100);
        }
//        int[] array = {50, 45, 11, 29, 39, 26, 9, 84, 20, 16};
        System.out.println("排序前：");
        for (int i : array) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println();

        sort(array, 0, array.length - 1);
        System.out.println("排序后：");
        for (int i : array) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println();

    }

    public static void sort(int[] array, int left, int right) {
        int low = left;
        int high = right;
        while (low < high) {
            int comparedNum = array[low];
            //右侧
            while (high > low && array[high] > comparedNum) {
                high--;
            }
            if (high > low) {
                array[low] = array[high];
                array[high] = comparedNum;
            }
            //左侧
            while (low < high && array[low] < comparedNum) {
                low++;
            }
            if (low < high) {
                array[high] = array[low];
                array[low] = comparedNum;
            }
        }
        sort(array, 0, low - 1);
        sort(array, low + 1, right);
    }
}
