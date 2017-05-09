package lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Edgar on 2016/1/19.
 *
 * @author Edgar  Date 2016/1/19
 */
public class LambdaExample {
  public static void main(String[] args) {

//    Use case Examples of lambdas
//    A boolean expression (List<String> list) -> list.isEmpty()
//    Creating objects () -> new Apple(10)
//    Consuming from an object (Apple a) -> {
//      System.out.println(a.getWeight());
//    }
//    Select/extract from an object (String s) -> s.length()
//    Combine two values (int a, int b) -> a * b
//    Compare two objects (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight())
//
    List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
    Collections.sort(names, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o1.compareTo(o2);
      }
    });


// Lambda表达式
    Collections.sort(names, (String o1, String o2) -> {
      return o1.compareTo(o2);
    });

    Collections.sort(names, (String o1, String o2) -> o1.compareTo(o2));

    Collections.sort(names, (o1, o2) -> o1.compareTo(o2));
  }
}
