package coll;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by edgar on 15-6-27.
 */
public class ImmutableTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testMultiMap() {
        Multimap<Integer,String> map = new
                ImmutableListMultimap.Builder<Integer,String>()
                .put(1,"Foo")
                .putAll(2,"Foo","Bar","Baz")
                .putAll(4,"Huey","Duey", "Luey")
                .put(3,"Single").build();
        map.put(1, "d");
        Assert.fail();
    }
}
