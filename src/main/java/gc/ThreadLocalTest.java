package gc;

/**
 * Created by Administrator on 2015/4/17.
 */
public class ThreadLocalTest {

    public static void main(String[] args) {
        ThreadLocal local = new ThreadLocal();
        ThreadLocal local2 = new ThreadLocal();
        local.get();
        local2.get();
    }
}
