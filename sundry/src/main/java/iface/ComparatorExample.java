package iface;

import lambda.Person;

import java.util.Comparator;

/**
 * Created by Edgar on 2016/1/19.
 *
 * @author Edgar  Date 2016/1/19
 */
public class ComparatorExample {
  public static void main(String[] args) {
    Comparator<Person> comparator = (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName());

    Person p1 = new Person("John", "Doe");
    Person p2 = new Person("Alice", "Wonderland");

    comparator.compare(p1, p2);             // > 0
    comparator.reversed().compare(p1, p2);  // < 0
  }
}
