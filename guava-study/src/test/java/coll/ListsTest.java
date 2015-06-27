package coll;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by edgar on 15-6-27.
 */
public class ListsTest {


    private Person person1;
    private Person person2;
    private Person person3;
    private Person person4;
    private List<Person> personList;

    @Before
    public void setUp() {
        person1 = new Person("Wilma", "Flintstone", 30, "F");
        person2 = new Person("Fred", "Flintstone", 32, "M");
        person3 = new Person("Betty", "Rubble", 31, "F");
        person4 = new Person("Barney", "Rubble", 33, "M");
        personList = Lists.newArrayList(person1, person2, person3, person4);
    }

    @Test
    public void testPartition() {
        List<List<Person>> subList = Lists.partition(personList, 2);
        System.out.println(subList);
    }

    @Test
    public void testReserve() {
        List<Person> reserve = Lists.reverse(personList);
        System.out.println(personList);
        System.out.println(reserve);
        Assert.assertSame(reserve.get(0), personList.get(3));
    }

    @Test
    public void testTransform() {
        List<String> transform = Lists.transform(personList, new Function<Person, String>() {
            @Override
            public String apply(Person input) {
                return input.getFirstname();
            }
        });
        System.out.println(transform);
    }
}
