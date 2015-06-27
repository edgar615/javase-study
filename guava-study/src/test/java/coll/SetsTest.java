package coll;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Created by edgar on 15-6-27.
 */
public class SetsTest {

    //差集
    @Test
    public void testDifference() {
        Set<Integer> s1 = Sets.newHashSet(1, 2, 3);
        Set<Integer> s2 = Sets.newHashSet(2, 3, 4);
        Sets.SetView<Integer> setView = Sets.difference(s1, s2);
        System.out.println(setView);//[1]
        System.out.println(Sets.difference(s2, s1));//[4]
    }

    //差集
    @Test
    public void testSymmetricDifference() {
        Set<Integer> s1 = Sets.newHashSet(1, 2, 3);
        Set<Integer> s2 = Sets.newHashSet(2, 3, 4);
        Sets.SetView<Integer> setView = Sets.symmetricDifference(s1, s2);
        System.out.println(setView);//[1, 4]
        s1.add(4);
        System.out.println(setView);//[1]
    }

    //交集
    @Test
    public void testInterSection() {
        Set<Integer> s1 = Sets.newHashSet(1, 2, 3);
        Set<Integer> s2 = Sets.newHashSet(2, 3, 4);
        Sets.SetView<Integer> setView = Sets.intersection(s1, s2);
        System.out.println(setView);//[2, 3]
        s1.add(4);
        System.out.println(setView);//[2, 3, 4]
    }

    //并集
    @Test
    public void testUnion() {
        Set<Integer> s1 = Sets.newHashSet(1, 2, 3);
        Set<Integer> s2 = Sets.newHashSet(2, 3, 4);
        Sets.SetView<Integer> setView = Sets.union(s1, s2);
        System.out.println(setView);//[1, 2, 3, 4]
        s1.add(5);
        System.out.println(setView);//[1, 2, 3, 5, 4]

    }

    //powerSet
    @Test
    public void testPowerSet() {
        Set<Integer> s1 = Sets.newHashSet(1, 2, 3);
        Set<Set<Integer>> setView = Sets.powerSet(s1);
        System.out.println(setView);//powerSet({1=0, 2=1, 3=2})

    }

    //cartesianProduct
    @Test
    public void testCartesianProduct() {
        Set<List<Object>> set = Sets.cartesianProduct(ImmutableSet.of(1, 2), ImmutableSet.of("A", "B", "C"));
        System.out.println(set);//[[1, A], [1, B], [1, C], [2, A], [2, B], [2, C]]

    }
}
