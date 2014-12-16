package concurrencyinpractice.chapter11;

import concurrencyinpractice.annotation.GuardedBy;
import concurrencyinpractice.annotation.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@ThreadSafe
public class AttributeStore {
    @GuardedBy("this") private final Map<String, String>
            attributes = new HashMap<String, String>();

    public synchronized boolean userLocationMatches(String name,
                                                    String regexp) {
        String key = "users." + name + ".location";
        String location = attributes.get(key);
        if (location == null)
            return false;
        else
            return Pattern.matches(regexp, location);
    }
}