package coll;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by edgar on 15-6-27.
 */
public class FluentIterableTest {

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
    public void testFilter() {
        Iterable<Person> personsFilteredByAge = FluentIterable.from(personList).filter(new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getAge() > 31;
            }
        });
        Assert.assertTrue(Iterables.contains(personsFilteredByAge, person2));
        Assert.assertTrue(Iterables.contains(personsFilteredByAge, person4));
        Assert.assertFalse(Iterables.contains(personsFilteredByAge, person1));
        Assert.assertFalse(Iterables.contains(personsFilteredByAge, person3));
    }

    @Test
    public void testTransform() {
        List<String> transformList = FluentIterable.from(personList).transform(new Function<Person, String>() {
            @Override
            public String apply(Person input) {
                return Joiner.on("#").join(input.getFirstname(), input.getLastname(), input.getAge());
            }
        }).toList();
        Assert.assertEquals(transformList.get(0), "Wilma#Flintstone#30");
    }

    @Test
    public void testAllMatch() {
        boolean allMatch = FluentIterable.from(personList).allMatch(new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getAge() > 31;
            }
        });
        Assert.assertFalse(allMatch);

        allMatch = FluentIterable.from(personList).allMatch(new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getAge() > 1;
            }
        });
        Assert.assertTrue(allMatch);
    }

    @Test
    public void testAnyMatch() {
        boolean anyMatch = FluentIterable.from(personList).anyMatch(new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getAge() > 31;
            }
        });
        Assert.assertTrue(anyMatch);

        anyMatch = FluentIterable.from(personList).anyMatch(new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getAge() > 50;
            }
        });
        Assert.assertFalse(anyMatch);
    }

    @Test
    public void testFirstMatch() {
        Optional<Person> firstMatch = FluentIterable.from(personList).firstMatch(new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getAge() < 31;
            }
        });

        Assert.assertTrue(firstMatch.isPresent());

        firstMatch = FluentIterable.from(personList).firstMatch(new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getAge() < 30;
            }
        });

        Assert.assertFalse(firstMatch.isPresent());
    }

    @Test
    public void testFirst() {
        Optional<Person> first = FluentIterable.from(personList).first();
        Assert.assertTrue(first.isPresent());
    }

    @Test
    public void testLast() {
        Optional<Person> last = FluentIterable.from(personList).last();
        Assert.assertTrue(last.isPresent());
    }

    @Test
    public void testCycle() {
        FluentIterable<Person> cycle = FluentIterable.from(personList).cycle();
        Assert.assertSame(cycle.get(5), cycle.get(1));
    }

    @Test
    public void testLimit() {
        FluentIterable<Person> cycle = FluentIterable.from(personList).limit(2);
        Assert.assertEquals(2, cycle.size());
    }

    @Test
    public void testAppend() {
        FluentIterable<Person> cycle = FluentIterable.from(personList).append(new Person("Edgar", "Zhang", 29, "M"));
        Assert.assertEquals(5, cycle.size());
    }

    @Test
    public void testSkip() {
        FluentIterable<Person> cycle = FluentIterable.from(personList).skip(3);
        Assert.assertEquals(1, cycle.size());
    }

    @Test
    public void testJoin() {
        String join = FluentIterable.from(personList).join(Joiner.on(";"));
        System.out.println(join);
    }

    @Test
    public void testIndex() {
        FluentIterable<Person> cycle = FluentIterable.from(personList).append(new Person("Edgar", "Zhang", 30, "M"));
        ImmutableListMultimap<Integer, Person> index =  FluentIterable.from(cycle.toList()).index(new Function<Person, Integer>() {
            @Override
            public Integer apply(Person input) {
                return input.getAge();
            }
        });
        System.out.println(index);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUniqueIndex() {
        FluentIterable<Person> cycle = FluentIterable.from(personList).append(new Person("Edgar", "Zhang", 30, "M"));
        ImmutableMap<Integer, Person> index =  FluentIterable.from(cycle.toList()).uniqueIndex(new Function<Person, Integer>() {
            @Override
            public Integer apply(Person input) {
                return input.getAge();
            }
        });
        Assert.fail();
        System.out.println(index);
    }

    @Test
    public void testToMap() {
        ImmutableMap<Person, String> index =  FluentIterable.from(personList).toMap(new Function<Person, String>() {
            @Override
            public String apply(Person input) {
                return input.getFirstname();
            }
        });
        System.out.println(index);
    }

    @Test
    public void testToMap2() {
        List<Person> persons = Lists.newArrayList(new Person("Edgar", "Zhang", 30, "M"));
        List<Person> index =  FluentIterable.from(personList).copyInto(persons);
        System.out.println(index);
    }
}
