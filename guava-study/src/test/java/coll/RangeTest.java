package coll;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by edgar on 15-6-27.
 */
public class RangeTest {

    @Test
    public void testRange() {
        Range<Integer> range = Range.closed(1, 10);
        System.out.println(range);//[1‥10]
        Assert.assertTrue(range.contains(1));
        Assert.assertTrue(range.contains(10));
        Assert.assertFalse(range.contains(11));

        range = Range.open(1, 10);
        System.out.println(range);//(1‥10)
        Assert.assertFalse(range.contains(1));
        Assert.assertFalse(range.contains(10));
        Assert.assertFalse(range.contains(11));

        range = Range.openClosed(1, 10);
        System.out.println(range);//(1‥10]
        Assert.assertFalse(range.contains(1));
        Assert.assertTrue(range.contains(10));
        Assert.assertFalse(range.contains(11));

        range = Range.closedOpen(1, 10);
        System.out.println(range);//[1‥10)
        Assert.assertTrue(range.contains(1));
        Assert.assertFalse(range.contains(10));
        Assert.assertFalse(range.contains(11));
    }

    @Test
    public void testRange2() {
        Person person1 = new Person("Wilma", "Flintstone", 30, "F");
        Person person2 = new Person("Fred", "Flintstone", 32, "M");
        Person person3 = new Person("Betty", "Rubble", 31, "F");
        Person person4 = new Person("Barney", "Rubble", 33, "M");
        List<Person> personList = Lists.newArrayList(person1, person2, person3, person4);
        Range<Integer> ageRange = Range.closed(35, 50);
        Function<Person, Integer> ageFunction = new Function<Person, Integer>() {
            @Override
            public Integer apply(Person input) {
                return input.getAge();
            }
        };

//        Predicate<Person> predicate =
//                Predicates.compose(ageRange,ageFunction);
    }
}
