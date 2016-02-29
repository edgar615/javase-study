package iface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Edgar on 2016/1/19.
 *
 * @author Edgar  Date 2016/1/19
 */
public class StreamExample {

  public static void main(String[] args) {
//    java.util.Stream 表示能应用在一组元素上一次执行的操作序列。
// Stream 操作分为中间操作或者最终操作两种，最终操作返回一特定类型的计算结果，而中间操作返回Stream本身，这样你就可以将多个操作依次串起来。
// Stream 的创建需要指定一个数据源，比如 java.util.Collection的子类，List或者Set， Map不支持。Stream的操作可以串行执行或者并行执行
    List<String> stringCollection = new ArrayList<>();
    stringCollection.add("ddd2");
    stringCollection.add("aaa2");
    stringCollection.add("bbb1");
    stringCollection.add("aaa1");
    stringCollection.add("bbb3");
    stringCollection.add("ccc");
    stringCollection.add("bbb2");
    stringCollection.add("ddd1");

    stringCollection.stream().filter((s) -> true).forEach(System.out::println);

//    Filter 过滤
//    过滤通过一个predicate接口来过滤并只保留符合条件的元素，该操作属于中间操作，所以我们可以在过滤后的结果来应用其他Stream操作（比如forEach）。
// forEach需要一个函数来对过滤后的元素依次执行。forEach是一个最终操作，所以我们不能在forEach之后来执行其他Stream操作。
    stringCollection.stream().filter((s) -> s.startsWith("a")).forEach(System.out::println);

//    Sort 排序
///    排序是一个中间操作，返回的是排序好后的Stream。如果你不指定一个自定义的Comparator则会使用默认排序。
    stringCollection.stream().sorted((s1, s2) -> s1.compareTo(s2)).filter((s) -> s.startsWith
            ("a")).forEach(System.out::println);

    //需要注意的是，排序只创建了一个排列好后的Stream，而不会影响原有的数据源，排序之后原数据stringCollection是不会被修改的：
    System.out.println(stringCollection);//[ddd2, aaa2, bbb1, aaa1, bbb3, ccc, bbb2, ddd1]

//    Map 映射
//    中间操作map会将元素根据指定的Function接口来依次将元素转成另外的对象，下面的示例展示了将字符串转换为大写字符串。
// 你也可以通过map来讲对象转换成其他类型，map 返回的Stream类型是根据你map传递进去的函数的返回值决定的。
    stringCollection.stream().map((s) -> s.toUpperCase()).filter((s) -> s.startsWith("A"))
            .forEach(System.out::println);

//    Match 匹配
//    Stream提供了多种匹配操作，允许检测指定的Predicate是否匹配整个Stream。所有的匹配操作都是最终操作，并返回一个boolean类型的值。
    System.out.println(stringCollection.stream().allMatch((s) -> s.startsWith("a")));
    System.out.println(stringCollection.stream().anyMatch((s) -> s.startsWith("a")));
    System.out.println(stringCollection.stream().noneMatch((s) -> s.startsWith("f")));

//    Count 计数
//    计数是一个最终操作，返回Stream中元素的个数，返回值类型是long。
    System.out.println(stringCollection.stream().filter((s) -> s.startsWith("b")).count());

//    Reduce 规约
//    这是一个最终操作，允许通过指定的函数来讲stream中的多个元素规约为一个元素，规越后的结果是通过Optional接口表示的：
    Optional<String> optional = stringCollection.stream().reduce((s1, s2) -> s1 + "," + s2);
    System.out.println(optional.get());

//    并行Streams
//    前面提到过Stream有串行和并行两种，串行Stream上的操作是在一个线程中依次完成，而并行Stream则是在多个线程上同时执行。 itxxz.com
//    下面的例子展示了是如何通过并行Stream来提升性能：
//    首先我们创建一个没有重复元素的大表：
    int max = 1000000;
    List<String> values = new ArrayList<>(max);
    for (int i = 0; i < max; i++) {
      UUID uuid = UUID.randomUUID();
      values.add(uuid.toString());
    }
    //串行排序
    long t0 = System.nanoTime();
    long count = values.stream().sorted().count();
    System.out.println(count);

    long t1 = System.nanoTime();
    long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
    System.out.println(String.format("sequential sort took: %d ms", millis));//sequential sort took: 626 ms

    //并行排序
     t0 = System.nanoTime();
     count = values.parallelStream().sorted().count();
    System.out.println(count);

     t1 = System.nanoTime();
    millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
    System.out.println(String.format("sequential sort took: %d ms", millis));//sequential sort took: 365 ms
  }
}
