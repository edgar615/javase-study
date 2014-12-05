package concurrencyinpractice.chapter04;

import concurrencyinpractice.annotation.GuardedBy;
import concurrencyinpractice.annotation.ThreadSafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ThreadSafe
 public class MonitorVehicleTracker {
    @GuardedBy("this") private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint loc = locations.get(id);
        return loc == null ? null : new MutablePoint(loc);
    }

    public synchronized void setLocation(String id, int x, int y) {
        MutablePoint loc = locations.get(id);
        if (loc == null)
            throw new IllegalArgumentException("No such ID: " + id);
        loc.x = x;
        loc.y = y;
    }

    //deepCopy并不只是用unmodifiableMap来包装Map,因为这只能防止容器对象被修改，而不能
    //防止调用者修改保存在容器中的可变对象。基于同样的原因，如果只是通过拷贝构造函数
    //来填充deepCopy中的HashMap，那么同样是不正确的，因为这样做只复制了指向Point对象的引用，
    //而不是Point本身
    private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
        Map<String, MutablePoint> result = new HashMap<String, MutablePoint>();

        for (String id : m.keySet())
            result.put(id, new MutablePoint(m.get(id)));

        return Collections.unmodifiableMap(result);
    }
}