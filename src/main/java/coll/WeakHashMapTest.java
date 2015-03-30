package coll;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by Administrator on 2015/3/30.
 */
public class WeakHashMapTest {

    public static void main(String[] args) {
        String a = new String("a");
        String b = new String("b");
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put(a, "A");
        hashMap.put(b, "B");

        Map<String, String> weakHashMap = new WeakHashMap<>();
        weakHashMap.put(a, "A");
        weakHashMap.put(b, "B");

        System.out.println(hashMap);
        System.out.println(weakHashMap);

        hashMap.remove(a);
        a = null;
        b = null;

        System.gc();

        System.out.println(hashMap);
        System.out.println(weakHashMap);
    }
}
