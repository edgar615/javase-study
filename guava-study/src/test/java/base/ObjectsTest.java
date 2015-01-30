package base;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2015/1/23.
 */
public class ObjectsTest {

    @Test(expected = NullPointerException.class)
    public void testFirstNonNull() {
        List<Integer> first = ImmutableList.of(1, 2);
        List<Integer> second = null;
        List<Integer> result = MoreObjects.firstNonNull(first, second);
        Assert.assertSame(first, result);
        result = MoreObjects.firstNonNull(second, first);
        Assert.assertSame(first, result);

        MoreObjects.firstNonNull(null, null);
        Assert.fail();
    }

    @Test
    public void testToStringHelper() {
        Assert.assertEquals("ObjString{x=1}", MoreObjects.toStringHelper("ObjString").add("x", 1).toString());
        Assert.assertEquals("Object{x=1}", MoreObjects.toStringHelper(Object.class).add("x", 1).toString());
        Assert.assertEquals("Object{x=1}", MoreObjects.toStringHelper(new Object()).add("x", 1).toString());
        Assert.assertEquals("Object{x=1, y=str}", MoreObjects.toStringHelper(new Object()).add("x", 1).add("y", "str").toString());
        Assert.assertEquals("Object{x=1, y=null}", MoreObjects.toStringHelper(new Object()).add("x", 1).add("y", null).toString());
        Assert.assertEquals("Object{x=1}", MoreObjects.toStringHelper(new Object()).omitNullValues().add("x", 1).add("y", null).toString());
    }

    @Test
    public void testEquals() {
        Assert.assertTrue(Objects.equal("a", "a"));
        Assert.assertFalse(Objects.equal("a", "A"));
    }

    @Test
    public void testHashCode() {
        System.out.println(Objects.hashCode(new Object()));
        System.out.println(Objects.hashCode(new Object(), new Object()));
    }

}
