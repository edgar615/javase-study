package concurreny;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Administrator on 2014/12/8.
 */
public class ConcurrentHashMapTest {

    @Test
    public void testPutIfAbsent() {
        ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
        System.out.println(map.putIfAbsent("key", "a"));
        System.out.println(map.putIfAbsent("key", "b"));
        System.out.println(map.putIfAbsent("key", "b"));
        System.out.println(map.putIfAbsent("key", "c"));
        System.out.println(map);

        System.out.println(map.put("key", "c"));
        System.out.println(map);
        System.out.println(map.put("key", "d"));
        System.out.println(map);
    }
}
