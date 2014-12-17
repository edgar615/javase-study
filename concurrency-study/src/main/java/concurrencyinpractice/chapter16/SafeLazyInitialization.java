package concurrencyinpractice.chapter16;

import concurrencyinpractice.annotation.ThreadSafe;

@ThreadSafe
public class SafeLazyInitialization {
    private static Resource resource;

    public synchronized static Resource getInstance() {
        if (resource == null)
            resource = new Resource();
        return resource;
    }

    static class Resource {
    }
}