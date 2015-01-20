package base;

import com.google.common.base.*;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.regex.Pattern;


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

    @Test
    public void testIdentity() {
        Object obj = new Object();
        Object value = Functions.identity().apply(obj);
        Assert.assertSame(obj, value);
    }

    @Test
    public void testToString() {
        Set<Integer> set = ImmutableSet.of(1, 2);
        System.out.println(Functions.toStringFunction().apply(set));
        System.out.println(Functions.toStringFunction().apply(ImmutableSet.of(2, 1)));
    }

    @Test
    public void testConstant() {
        Set<Integer> set = ImmutableSet.of(1, 2);
        System.out.println(Functions.constant("CONST VALUE").apply(set));
        System.out.println(Functions.constant("CONST VALUE").apply(ImmutableSet.of(2, 1)));
    }

    @Test
    public void testCompose() {
        Set<Integer> set = ImmutableSet.of(1, 2);
        System.out.println(Functions.compose(Functions.constant("CONST VALUE"), Functions.toStringFunction()).apply(set));
    }

    @Test
    public void testPredicate() {
        Predicate<Integer> predicate = new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input < 0;
            }
        };
        Assert.assertFalse(predicate.apply(1));
    }

    @Test
    public void testInstanceOf() {
        HashMap map = new HashMap();
        Assert.assertTrue(Predicates.instanceOf(Map.class).apply(map));
    }

    @Test
    public void testAssignableFrom() {
        Assert.assertTrue(Predicates.assignableFrom(Map.class).apply(HashMap.class));
    }

    @Test
    public void testContains() {
        Assert.assertTrue(Predicates.contains(Pattern.compile("[0-3]{3}")).apply("123"));
        Assert.assertFalse(Predicates.contains(Pattern.compile("[0-3]{3}")).apply("34"));
    }
}
