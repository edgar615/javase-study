package concurrencyinpractice.chapter16;

/**
 * Created by Administrator on 2014/12/17.
 */
public class SingleInstance {
    private static class Holder {
        public static Resource resource = new Resource();
    }

    public static Resource getResource() {
        return Holder.resource;
    }

    static class Resource {
        public Resource() {
            System.out.println("resource");
        }
    }
}
