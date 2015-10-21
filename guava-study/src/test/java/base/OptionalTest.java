package base;

import com.google.common.base.Optional;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Created by Administrator on 2015/1/23.
 */
public class OptionalTest {

    @Test
    public void testOf() {
        Optional<Integer> optional = Optional.of(5);
        Assert.assertTrue(optional.isPresent());
        Assert.assertEquals(5, optional.get(), 0);
    }

    @Test(expected = NullPointerException.class)
    public void testOf2() {
        Optional<String> optional = Optional.of("A");
        optional = Optional.of(null);
        Assert.fail();
    }

    @Test
    public void testAbsent() {
        Optional<String> optional = Optional.absent();
        Assert.assertFalse(optional.isPresent());
    }

    @Test
    public void testFromNullable() {
        Optional<String> optional = Optional.fromNullable("A");
        Assert.assertTrue(optional.isPresent());

        optional = Optional.fromNullable(null);
        Assert.assertFalse(optional.isPresent());
    }

    @Test(expected = IllegalStateException.class)
    public void testGet() {
        Optional<String> optional = Optional.fromNullable("A");
        Assert.assertTrue(optional.isPresent());
        Assert.assertEquals("A", optional.get());

        optional = Optional.absent();
        Assert.assertFalse(optional.isPresent());
        optional.get();
        Assert.fail();
    }

    @Test
    public void testOr() {
        Optional<String> optional = Optional.of("A");
        Assert.assertEquals("A", optional.or("B"));

        optional = Optional.absent();
        Assert.assertEquals("B", optional.or("B"));
    }

    @Test
    public void testOrNull() {
        Optional<String> optional = Optional.of("A");
        Assert.assertEquals("A", optional.orNull());
        System.out.println(optional.orNull());

        optional = Optional.absent();
        System.out.println(optional.orNull());
        Assert.assertNull(optional.orNull());
    }

    @Test
    public void testAsSet() {
        Optional<String> optional = Optional.of("A");
        Set<String> set = optional.asSet();
        Assert.assertEquals(1, set.size());
        Assert.assertEquals("A",set.iterator().next());
    }
}
