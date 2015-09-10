package sort;

import java.util.Random;

/**
 * Created by edgar on 15-5-14.
 */
public class InsertSort {
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

        sort(array);
        System.out.println("排序后：");
        for (int i : array) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println();

    }

    public static void sort(int[] array) {
        int length = array.length;
        for (int i = 1; i < length; i ++) {
            for (int j = 0; j < i; j ++) {
                if (array[j] > array[i]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }

    }
}
