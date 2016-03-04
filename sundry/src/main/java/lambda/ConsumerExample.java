package lambda;

import java.util.function.Consumer;

/**
 * Created by Edgar on 2016/1/19.
 *
 * @author Edgar  Date 2016/1/19
 */
public class ConsumerExample {

  public static void main(String[] args) {
    //Consumer 接口表示执行在单个参数上的操作
    Consumer<String> consumer = (s) -> System.out.println(s);
    consumer.accept("Hello World");
  }
}
