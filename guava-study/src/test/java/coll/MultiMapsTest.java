package coll;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by edgar on 15-6-27.
 */
public class MultiMapsTest {
    @Test
    public void testArrayListMultiMap(){
        ArrayListMultimap<String,String> multiMap =
                ArrayListMultimap.create();
        multiMap.put("Foo","1");
        multiMap.put("Foo","2");
        multiMap.put("Foo","3");
        List<String> expected = Lists.newArrayList("1", "2", "3");
        assertEquals(multiMap.get("Foo"),expected);
    }

    @Test
    public void testArrayListMultimapSameKeyValue(){
        ArrayListMultimap<String,String> multiMap =
                ArrayListMultimap.create();
        multiMap.put("Bar","1");
        multiMap.put("Bar","2");
        multiMap.put("Bar","3");
        multiMap.put("Bar","3");
        multiMap.put("Bar","3");
        List<String> expected = Lists.
                newArrayList("1","2","3","3","3");
        assertEquals(multiMap.get("Bar"),expected);

        multiMap =
                ArrayListMultimap.create();
        multiMap.put("Foo","1");
        multiMap.put("Foo","2");
        multiMap.put("Foo","3");
        multiMap.put("Bar","1");
        multiMap.put("Bar","2");
        multiMap.put("Bar","3");
        expected = Lists.
                newArrayList("1","2","3");
        assertEquals(multiMap.get("Bar"),expected);
    }

    @Test
    public void testArrayListMultimapNum(){
        ArrayListMultimap<String,String> multiMap =
                ArrayListMultimap.create(2, 2);
        multiMap.put("Foo","1");
        multiMap.put("Foo","2");
        multiMap.put("Foo","3");
        multiMap.put("Bar","1");
        multiMap.put("Bar","2");
        multiMap.put("Bar","3");
        List<String> expected = Lists.
                newArrayList("1","2","3");
        assertEquals(multiMap.get("Bar"),expected);

    }

    @Test
    public void testHashMultiMap(){
        HashMultimap<String,String> multiMap =
                HashMultimap.create();
        multiMap.put("Bar","1");
        multiMap.put("Bar","2");
        multiMap.put("Bar","3");
        multiMap.put("Bar","3");
        multiMap.put("Bar","3");
        Set<String> expected = Sets.
                newHashSet("1", "2", "3");
        assertEquals(multiMap.get("Bar"),expected);

    }
}
