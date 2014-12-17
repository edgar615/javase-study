package concurrencyinpractice.chapter16;

import concurrencyinpractice.annotation.NotThreadSafe;

@NotThreadSafe
public class UnsafeLazyInitialization {
    private static Resource resource;

    public static Resource getInstance() {
        if (resource == null)
            resource = new Resource(); // unsafe publication
        return resource;
    }

    static class Resource {
    }
}