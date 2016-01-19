package lambda;

/**
 * Created by Edgar on 2016/1/19.
 *
 * @author Edgar  Date 2016/1/19
 */
public class InnerExample {
  public static void main(String[] args) {
    int num = 1;//num必须不可被后面的代码修改（即隐性的具有final的语义）
    Converter<Integer, String> converter = (from) -> String.valueOf(num + from);
    System.out.println(converter.convert(2));
//    num = 3;
  }
}
