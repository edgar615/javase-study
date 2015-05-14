package sort;

import java.util.Random;

/**
 * Created by Administrator on 2015/5/14.
 */
public class QuitSort {

    public static void main(String[] args) {
        Random r = new Random();
//        int[] array = new int[10];
//        for (int i = 0; i < 10; i ++) {
//            array[i] = r.nextInt(100);
//        }
        int[] array = {50,45,11,29,39,26,9,84,20,16};
        for (int i : array) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println();
        System.out.println("开始");

        int left = 0;
        int right = array.length - 1;
        System.out.println(sort(array, left, right));


//        while (right > left) {
//            sort(array, left, right);
//            right --;
//            for (int i : array) {
//                System.out.print(i);
//                System.out.print(",");
//            }
//            System.out.println();
//            System.out.println("完成一次排序");
//        }

    }

    public static int sort(int[] array, int left, int right) {
        int middle = left;
        while (middle < right) {
            int comparedNum = array[middle];
            //右侧
            while (right > middle && array[right] >= comparedNum) {
                right--;
            }
            if (right > middle) {
                array[middle] = array[right];
                array[right] = comparedNum;
            }

            //左侧
            while (middle < right && array[middle] < comparedNum) {
                middle++;
            }
            if (middle <= right) {
                array[right] = array[middle];
                array[middle] = comparedNum;
            }
        }
        for (int i : array) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println(middle);
        if (middle - 1 > 0) {
           middle = sort(array, 0, middle -1);
        }
        if (right >= left + 1) {
            middle = sort(array, middle + 1, right);
        }
        return middle;
    }
}
