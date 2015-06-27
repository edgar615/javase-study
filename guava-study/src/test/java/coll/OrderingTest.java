package coll;

import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by edgar on 15-6-27.
 */
public class OrderingTest {
    private Person2 person1;
    private Person2 person2;
    private Person2 person3;
    private Person2 person4;
    private List<Person2> personList;

    @Before
    public void setUp() {
        person1 = new Person2("Wilma", "Flintstone", 30, "F");
        person2 = new Person2("Fred", "Flintstone", 30, "M");
        person3 = new Person2("Betty", "Rubble", 31, "F");
        person4 = new Person2("Barney", "Rubble", 33, "M");
        personList = Lists.newArrayList(person1, person2, person3, person4);
    }
    @Test
    public void test() {
        System.out.println(personList);
        Ordering<Person2> personOrdering = Ordering.from(new PersonSort1()).nullsFirst().reverse();
        Collections.sort(personList, personOrdering);
        System.out.println(personList);
        Collections.sort(personList, Ordering.from(new PersonSort1()).compound(new PersonSort2()));
        System.out.println(personList);

        List<Person2> topThree = personOrdering.greatestOf(personList,3);
        List<Person2> bottomTwo = personOrdering.leastOf(personList,2);
        System.out.println(topThree);
        System.out.println(bottomTwo);
    }

    public class PersonSort1 implements Comparator<Person2> {

        @Override
        public int compare(Person2 o1, Person2 o2) {
            return Ints.compare(o1.getAge(), o2.getAge());
        }
    }

    public class PersonSort2 implements Comparator<Person2> {

        @Override
        public int compare(Person2 o1, Person2 o2) {
            return Ints.compare(o1.getFirstname().length(), o2.getFirstname().length());
        }
    }
}
