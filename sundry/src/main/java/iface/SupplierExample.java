package iface;

import lambda.Person;

import java.util.function.Supplier;

/**
 * Created by Edgar on 2016/1/19.
 *
 * @author Edgar  Date 2016/1/19
 */
public class SupplierExample {
  public static void main(String[] args) {
    //Supplier 接口返回一个任意范型的值，和Function接口不同的是该接口没有任何参数
    Supplier<Person> personSupplier = Person::new;
    Person person = personSupplier.get();
  }
}
