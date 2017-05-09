package lambda;

import parameterize.Apple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Edgar on 2016/4/6.
 *
 * @author Edgar  Date 2016/4/6
 */
public class Example {

  public static void main(String[] args) {
    List<Apple> inventory = Arrays
            .asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

    inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));

    inventory.sort(Comparator.comparing((a) -> a.getWeight()));

    inventory.sort(Comparator.comparing(Apple::getWeight));
  }
}
