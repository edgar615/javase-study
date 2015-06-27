package coll;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by edgar on 15-6-27.
 */
public class BiMapTest {

    @Test(expected = IllegalArgumentException.class)
    public void testBiMap() {
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("1", "Edgar");
        biMap.put("2", "Edgar");//java.lang.IllegalArgumentException: value already present: Edgar
        Assert.fail();
    }

    @Test
    public void testForcePut() {
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("1", "Edgar");
        biMap.forcePut("2", "Edgar");
        Assert.assertTrue(biMap.containsKey("2"));
        Assert.assertFalse(biMap.containsKey("1"));
    }

    @Test
    public void testInverse() {
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("1", "Edgar");
        biMap.put("2", "Adonis");
        Assert.assertEquals(biMap.get("1"), "Edgar");
        Assert.assertEquals(biMap.get("2"), "Adonis");

        BiMap<String,String> inverseMap = biMap.inverse();
        Assert.assertEquals(inverseMap.get("Edgar"), "1");
        Assert.assertEquals(inverseMap.get("Adonis"), "2");
    }
}
