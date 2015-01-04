package base;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2015/1/4.
 */
public class FunctionTest {

    @Test
    public void testFunction() {
        Function<String, Integer> lengthFunction = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        };
        System.out.println(lengthFunction.apply("Edgar"));

        Predicate<String> allCaps = new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return CharMatcher.JAVA_UPPER_CASE.matchesAllOf(input);
            }
        };
        System.out.println(allCaps.apply("JAVA"));

        List<String> strings = new ArrayList<String>();
        strings.add("JAVA");
        strings.add("Java");
        strings.add("Edgar");
        strings.add("EDGAR");
        Multiset<Integer> lengths = HashMultiset.create(Iterables.transform(Iterables.filter(strings, allCaps), lengthFunction));
        System.out.println(lengths);
    }

    @Test
    public void testCommand() {
        List<String> strings = new ArrayList<String>();
        strings.add("JAVA");
        strings.add("Java");
        strings.add("Edgar");
        strings.add("EDGAR");
        Multiset<Integer> lengths = HashMultiset.create();
        for (String input : strings) {
            if (CharMatcher.JAVA_UPPER_CASE.matchesAllOf(input)) {
                lengths.add(input.length());
            }
        }
        System.out.println(lengths);
    }

    @Test
    public void testForMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", "Edgar");
        map.put("password", "test");
        map.put("sex", "male");
        String value = Functions.forMap(map).apply("username");
        System.out.println(value);
        value = Functions.forMap(map, "default").apply("email");
        System.out.println(value);
        value = Functions.forMap(map).apply("email");
        //throw java.lang.IllegalArgumentException
    }
}
