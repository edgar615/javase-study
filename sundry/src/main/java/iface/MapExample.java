package iface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Edgar on 2016/1/19.
 *
 * @author Edgar  Date 2016/1/19
 */
public class MapExample {

  public static void main(String[] args) {
    Map<Integer, String> map = new HashMap<>();

    for (int i = 0; i < 10; i++) {
      map.putIfAbsent(i, "val" + i);
    }
    map.forEach((id, val) -> System.out.println(val));

    map.computeIfPresent(3, (num, val) -> val + num);
    System.out.println(map.get(3));             // val33



    map.computeIfPresent(9, (num, val) -> null);
    System.out.println(map.containsKey(9));// false

    map.computeIfAbsent(23, num -> "val" + num);
    System.out.println(map.containsKey(23));   // true

    map.computeIfAbsent(3, num -> "bam");
    System.out.println(map.get(3));        // val33

//    删除一个键值全都匹配的项
    System.out.println(map.remove(3, "val33"));
    System.out.println(map.get(3));//null
    System.out.println(map.getOrDefault(3, "not found"));//not found

    // Merge做的事情是如果键名不存在则插入，否则则对原键对应的值做合并操作并重新插入到map中。
    map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
    System.out.println(map.get(9));//val9
    map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
    System.out.println(map.get(9));//val9concat


  }
}
