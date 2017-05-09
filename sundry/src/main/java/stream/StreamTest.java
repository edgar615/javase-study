package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Edgar on 2016/4/8.
 *
 * @author Edgar  Date 2016/4/8
 */
public class StreamTest {
  public static void main(String[] args) {
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    list.add(5);
    list.add(6);
    list.add(7);
    list.add(8);
    list.add(9);
    list.add(10);

   Optional<Integer> r = list.stream().filter((i) -> i % 2 == 0).findFirst();
    System.out.println(r.get());
     r = list.stream().filter((i) -> i > 20).findFirst();
    System.out.println(r.isPresent());
  }
}
