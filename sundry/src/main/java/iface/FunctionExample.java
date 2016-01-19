package iface;

import java.util.function.Function;

/**
 * Created by Edgar on 2016/1/19.
 *
 * @author Edgar  Date 2016/1/19
 */
public class FunctionExample {
  public static void main(String[] args) {
    //Function 接口有一个参数并且返回一个结果，并附带了一些可以和其他函数组合的默认方法（compose, andThen）：
    Function<String, Integer> toInteger = Integer::valueOf;
    Function<String, String> backToString = toInteger.andThen(String::valueOf);

    System.out.println(backToString.apply("123"));
  }
}
