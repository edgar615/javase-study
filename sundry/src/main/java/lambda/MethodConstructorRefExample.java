package lambda;

/**
 * Created by Edgar on 2016/1/19.
 *
 * @author Edgar  Date 2016/1/19
 */
public class MethodConstructorRefExample {

  //Java 8 允许你使用 :: 关键字来传递方法或者构造函数引用
  public static void main(String[] args) {

    //静态函数
    Converter<String, Integer> converter = Integer::parseInt;
    System.out.println(converter.convert("1"));

    //也可以引用一个对象的方法
    Converter<String, Boolean> converter1 = "Hello World"::startsWith;
    System.out.println(converter1.convert("Hello"));
    System.out.println(converter1.convert("Hi"));

    //构造函数
    //Person::new 来获取Person类构造函数的引用，Java编译器会自动根据PersonFactory.create方法的签名来选择合适的构造函数
    PersonFactory personFactory = Person::new;
    Person person = personFactory.create("Edgar", "Zhang");
  }
}
