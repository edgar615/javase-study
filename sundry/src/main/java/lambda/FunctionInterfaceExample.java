package lambda;

/**
 * Created by Edgar on 2016/1/19.
 *
 * @author Edgar  Date 2016/1/19
 */
public class FunctionInterfaceExample {
  public static void main(String[] args) {
    Converter<String, Integer> converter = (from) -> Integer.parseInt(from);
    System.out.println(converter.convert("1"));
  }
}
