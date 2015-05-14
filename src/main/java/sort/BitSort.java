package sort;

import java.util.Random;

/**
 * Created by edgar on 15-5-14.
 */
public class BitSort {

    public static void main(String[] args) {
        Random r = new Random();
        int[] array = new int[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = r.nextInt(100000);
        }
        System.out.println("排序前：");
        for (int i : array) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println();
        int[] newArray = sort(array, 100000);
        System.out.println("排序后：");
        for (int i : newArray) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println();
    }

    public static int[] sort(int[] array, int bitSize) {
        int[] bitArray = new int[bitSize];
        for (int i = 0; i < bitSize; i++) {
            bitArray[i] = 0;
        }
        int length = array.length;
        int newArrayLength = 0;
        for (int i = 0; i < length; i++) {
            if (bitArray[array[i]] == 1) {
                System.out.println(array[i] + "是重复值");
            } else {
                bitArray[array[i]] = 1;
                newArrayLength++;
            }
        }
        int index = 0;
        int[] newArray = new int[newArrayLength];
        for (int i = 0; i < bitSize; i++) {
            if (bitArray[i] == 1) {
                newArray[index] = i;
                index++;
            }
        }
        return newArray;
    }

}
