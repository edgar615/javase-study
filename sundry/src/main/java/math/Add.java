package math;

import java.util.Arrays;

/**
 * Created by Administrator on 2015/2/10.
 * 无限长数字的加减运算
 */
public class Add {

    private static int[] add(String num1, String num2) {
        int[] array1 = new int[0], array2 = new int[0], array3 = new int[0];
        //保证array1的长度等于array2的长度
        if (num1.length() > num2.length()){
            array1 = strToIntArray(num2);
            array2 = strToIntArray(num1.substring(num1.length() - num2.length(), num1.length()));
            array3 = strToIntArray(num1.substring(0, num1.length() - num2.length()));
        } else if (num1.length() < num2.length()){
            array1 = strToIntArray(num1);
            array2 = strToIntArray(num2.substring(num2.length() - num1.length(), num2.length()));
            array3 = strToIntArray(num2.substring(0, num2.length() - num1.length()));
        } else {
            array1 = strToIntArray(num1);
            array2 = strToIntArray(num2);
        }
        return addArray(array1, array2, array3);

    }

    private static int[] addArray(int[] array1, int[] array2, int[] array3) {
        int length1 = array1.length;
        int length2 = array2.length;
        if (length1 != length2) {
            throw new RuntimeException("两个数组的长度必须保证一致");
        }
        //进位数组，用来存放两个数相加产生的进位，进位多保存1位
        int[] carryArray = new int[length1 + 1];
        //进位数组初始化为0
        for (int i = 0; i < carryArray.length; i ++) {
            carryArray[i] = 0;
        }
        //结果数组，用来存储结果
        int[] result = new int[length1];
        for (int i = length2 - 1; i > -1; i --) {
            int add1 = array1[i];
            int add2 = array2[i];
            int carry = carryArray[i+1];//进位
            int sum = add1 + add2 + carry;
            if (sum > 9) {
                String sumStr = String.valueOf(sum);
                carryArray[i] = Character.getNumericValue(sumStr.codePointAt(0));
                result[i] = Character.getNumericValue(sumStr.codePointAt(1));
            } else {
                result[i] = sum;
            }
        }
        if (array3.length == 0) {
            if (carryArray[0] == 0) {
                return result;
            } else {
                int[] newResult = new int[result.length + 1];
                newResult[0] = carryArray[0];
                for (int i = 1; i < newResult.length; i ++) {
                    newResult[i] = result[i - 1];
                }
                return newResult;
            }
        } else {
            if (carryArray[0] == 0) {
                return concat(result, array3);
            } else {
                //进位再次与array3做加法
                array2 = new int[array3.length];
                for (int i = 0; i < array2.length; i ++) {
                    array2[i] = 0;
                }
                array2[array2.length - 1] = carryArray[0];
                return concat(result, addArray(array3, array2, new int[0]));
            }
        }
    }

    private static int[] concat(int[] array1, int[] array2) {
        int[] newResult = new int[array1.length + array2.length];
        for (int i = 0; i < array2.length; i ++) {
            newResult[i] = array2[i];
        }
        for (int i = array2.length; i < newResult.length; i ++) {
            newResult[i] = array1[i - array2.length];
        }
        return newResult;
    }

    //字符串转为int数组
    private static int[] strToIntArray(String input) {
        int length = input.length();
        int[] array = new int[length];
        for (int i = 0; i < length; i ++ ) {
            array[i] = Character.getNumericValue(input.codePointAt(i));
        }
        return array;
    }

    public static void main(String[] args) {
        //相同长度，无进位
        System.out.println(Arrays.toString(add("1234", "1234")));
        //相同长度，有进位
        System.out.println(Arrays.toString(add("1234", "9999")));

        //不同长度，无进位
        System.out.println(Arrays.toString(add("1234", "111111")));

        //不同长度，有进位
        System.out.println(Arrays.toString(add("1234", "999999")));
    }
}
