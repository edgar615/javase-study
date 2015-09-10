package reflection;

import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * Created by Edgar on 14-11-23.
 */
public class PorxyTest {

    @Test
    public void test() {
        MyService myService = new MyServiceImpl();
        myService.say();

        //proxy
        MyService proxy = (MyService) Proxy.newProxyInstance(myService.getClass().getClassLoader(), myService.getClass().getInterfaces(), new MyInvocationHandler(myService));
        proxy.say();

    }
}
