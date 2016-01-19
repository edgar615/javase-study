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
