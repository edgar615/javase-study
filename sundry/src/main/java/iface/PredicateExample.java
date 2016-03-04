package iface;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Created by Edgar on 2016/1/19.
 *
 * @author Edgar  Date 2016/1/19
 */
public class PredicateExample {
  public static void main(String[] args) {
    Predicate<String> predicate = (s) -> s.length() > 0;


    System.out.println(predicate.test("foo"));              // true
    System.out.println(predicate.negate().test("foo"));      // false

    Predicate<Boolean> nonNull = Objects::nonNull;
    Predicate<Boolean> isNull = Objects::isNull;

    Predicate<String> isEmpty = String::isEmpty;
    Predicate<String> isNotEmpty = isEmpty.negate();
  }
}
