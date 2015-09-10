package coll;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/3/30.
 */
public class IdentityHashMapTest {

   public static void main(String[] args) {
       String s1 = new String("key");
       String s2 = new String("key");
       Map<String, String> hashMap = new HashMap<String, String>();
       hashMap.put(s1, "s1");
       hashMap.put(s2, "s2");
       System.out.println(hashMap);

       Map<String, String> identityHashMap = new IdentityHashMap<String, String>();
       identityHashMap.put(s1, "s1");
       identityHashMap.put(s2, "s2");
       System.out.println(identityHashMap);
   }

}
