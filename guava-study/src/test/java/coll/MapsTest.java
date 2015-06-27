package coll;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by edgar on 15-6-27.
 */
public class MapsTest {

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
    public void testUniqueIndex() {
        Map<String, Person> map = Maps.uniqueIndex(personList.iterator(), new Function<Person, String>() {
            @Override
            public String apply(Person input) {
                return input.getFirstname();
            }
        });
        System.out.println(map);
        //{Wilma=coll.Person@782830e, Fred=coll.Person@470e2030, Betty=coll.Person@3fb4f649, Barney=coll.Person@33833882}
    }
}
