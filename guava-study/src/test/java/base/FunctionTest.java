package base;

import com.google.common.base.*;
import com.google.common.base.Optional;
import com.google.common.collect.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
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

    @Test
    public void testIn() {
        List<String> list = ImmutableList.of("1", "2");
        Assert.assertTrue(Predicates.in(list).apply("1"));
        Assert.assertFalse(Predicates.in(list).apply("3"));

    }

    @Test
    public void testIsNull() {
        Assert.assertFalse(Predicates.isNull().apply(new Object()));
        Assert.assertTrue(Predicates.isNull().apply(null));
    }

    @Test
    public void testAlwaysFalse() {
        Assert.assertFalse(Predicates.alwaysFalse().apply(true));
    }

    @Test
    public void testAlwaysTrue() {
        Assert.assertTrue(Predicates.alwaysTrue().apply(false));
    }

    @Test
    public void testEqualTo() {
        Assert.assertTrue(Predicates.equalTo(null).apply(null));
        Assert.assertTrue(Predicates.equalTo(new ArrayList<Integer>()).apply(new ArrayList<Integer>()));
    }

    @Test
    public void testCompose2() {
        Map<String, String> map = ImmutableMap.of("k1", "v1", "k2", "v2");
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return input.equals("v2");
            }
        };
        Assert.assertFalse(Predicates.compose(predicate, Functions.forMap(map)).apply("k1"));
        Assert.assertTrue(Predicates.compose(predicate, Functions.forMap(map)).apply("k2"));
    }

    @Test
    public void testAnd() {
        Assert.assertFalse(Predicates.and(Predicates.alwaysTrue(), Predicates.alwaysFalse()).apply("1"));
    }

    @Test
    public void testOr() {
        Assert.assertTrue(Predicates.or(Predicates.alwaysTrue(), Predicates.alwaysFalse()).apply("1"));
    }

    @Test
    public void testNot() {
        Assert.assertTrue(Predicates.not(Predicates.alwaysFalse()).apply("1"));
    }

    @Test
    public void testIterableFilter() {
        Iterable<Integer> iterable = Lists.newArrayList(1, 2);
        Iterable<Integer> iterableView = Iterables.filter(iterable, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input == 1;
            }
        });
        System.out.println(iterable);//[1, 2]
        System.out.println(iterableView);//[1]

        Iterables.removeIf(iterable, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 != 0;
            }
        });
        System.out.println(iterable);//[2]
        System.out.println(iterableView);//[]
    }

    @Test
    public void testIteratorFilter() {
        Iterator<Integer> iterator = ImmutableList.of(1, 2, 3).iterator();
        Iterator<Integer> iteratorView = Iterators.filter(iterator, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input < 3;
            }
        });
        while (iterator.hasNext()) {
            System.out.print(iterator.next());
        }
        // 123
        System.out.println();
        while (iteratorView.hasNext()) {
            System.out.print(iteratorView.next());
        }
        //无输出
    }

    @Test
    public void testIteratorFilter2() {
        Iterator<Integer> iterator = ImmutableList.of(1, 2, 3).iterator();
        Iterator<Integer> iteratorView = Iterators.filter(iterator, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input < 3;
            }
        });
        while (iteratorView.hasNext()) {
            System.out.print(iteratorView.next());
        }
        //12
        System.out.println();
        while (iterator.hasNext()) {
            System.out.print(iterator.next());
        }
        // 无输出

    }

    @Test
    public void testFilterKeys() {
        Map<String, String> map = ImmutableMap.of("k1", "v1", "k2", "v2");
        Map<String, String> mapView = Maps.filterKeys(map, new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return "k1".equals(input);
            }
        });
        System.out.println(map);//{k1=v1, k2=v2}
        System.out.println(mapView);//{k1=v1}
    }

    @Test
    public void testFilterValues() {
        Map<String, String> map = ImmutableMap.of("k1", "v1", "k2", "v2");
        Map<String, String> mapView = Maps.filterValues(map, new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return "v2".equals(input);
            }
        });
        System.out.println(map);//{k1=v1, k2=v2}
        System.out.println(mapView);//{k2=v2}
    }

    @Test
    public void testFilterEntries() {
        Map<String, String> map = ImmutableMap.of("k1", "v1", "k2", "v2");
        Map<String, String> mapView = Maps.filterEntries(map, new Predicate<Map.Entry<String, String>>() {
            @Override
            public boolean apply(Map.Entry<String, String> input) {
                return "k1".equals(input.getKey()) && "v1".equals(input.getValue());
            }
        });
        System.out.println(map);//{k1=v1, k2=v2}
        System.out.println(mapView);//{k1=v1}
    }

    @Test
    public void testIterableAll() {
        Iterable<Integer> iterable = ImmutableList.of(1, 2);
        Assert.assertTrue(Iterables.all(iterable, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input < 3;
            }
        }));
    }

    @Test
    public void testIterableAny() {
        Iterable<Integer> iterable = ImmutableList.of(1, 2);
        Assert.assertTrue(Iterables.any(iterable, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input == 1;
            }
        }));
    }

    @Test(expected = NoSuchElementException.class)
    public void testFind() {
        Iterable<Integer> iterable = ImmutableList.of(1, 2, 3, 4);
        int result = Iterables.find(iterable, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        });
        Assert.assertEquals(2, result);

        //java.util.NoSuchElementException
        Iterables.find(iterable, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input == 5;
            }
        });
        Assert.fail();
    }

    @Test
    public void testTryFind() {
        Iterable<Integer> iterable = ImmutableList.of(1, 2, 3, 4);
        Optional<Integer> result = Iterables.tryFind(iterable, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input == 2;
            }
        });
        Assert.assertEquals(2, result.or(0), 0);

        result = Iterables.tryFind(iterable, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input == 5;
            }
        });
        Assert.assertEquals(0, result.or(0), 0);
    }

    @Test
    public void testIndexOf() {
        Iterable<Integer> iterable = ImmutableList.of(1, 2, 3, 4);
        int index = Iterables.indexOf(iterable, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        });
        Assert.assertEquals(1, index);
        index = Iterables.indexOf(iterable, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input == 5;
            }
        });
        Assert.assertEquals(-1, index);
    }

    @Test
    public void testRemoveIf() {
        Iterable<Integer> iterable = Lists.newArrayList(1, 2, 3, 4);
        Iterables.removeIf(iterable, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        });
        System.out.println(iterable);
    }

    @Test
    public void testIterableTransform() {
        Iterable<Integer> iterable = Lists.newArrayList(1, 2, 3, 4);
        Iterable<String> iterableView =  Iterables.transform(iterable, new Function<Integer, String>() {
            @Override
            public String apply(Integer input) {
                return String.valueOf(input * 10);
            }
        });
        System.out.println(iterable);//[1, 2, 3, 4]
        System.out.println(iterableView);//[10, 20, 30, 40]
        Iterables.removeIf(iterable, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        });
        System.out.println(iterable);//[1, 3]
        System.out.println(iterableView);//[10, 30]
    }

    @Test
    public void testIteratorTransform() {
        final Iterator<Integer> iterator = ImmutableList.of(1, 2, 3, 4).iterator();
        Iterator<String> iteratorView =  Iterators.transform(iterator, new Function<Integer, String>() {
            @Override
            public String apply(Integer input) {
                return String.valueOf(input * 10);
            }
        });
        iterator.forEachRemaining(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.print(integer);
            }
        });
        //1234
        System.out.println();
        iteratorView.forEachRemaining(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.print(s);
            }
        });
        //输出为空
    }

    @Test
    public void testTransformValues() {
        Map<String, String> map = ImmutableMap.of("k1", "v1", "k2", "v2");
        Map<String, String> mapView = Maps.transformValues(map, new Function<String, String>() {
            @Override
            public String apply(String input) {
                return "[" + input + "]";
            }
        });
        System.out.println(map);//{k1=v1, k2=v2}
        System.out.println(mapView);//{k1=[v1], k2=[v2]}
    }

    @Test
    public void testTransformValues2() {
        Map<String, Boolean> map = ImmutableMap.of("verbose", true, "sort", false);
        Map<String, String> mapView = Maps.transformEntries(map, new Maps.EntryTransformer<String, Boolean, String>() {
            @Override
            public String transformEntry(String key, Boolean value) {
                return value ? key : "no" + key;
            }
        });
        System.out.println(map);//{verbose=true, sort=false}
        System.out.println(mapView);//{verbose=verbose, sort=nosort}
    }
}
