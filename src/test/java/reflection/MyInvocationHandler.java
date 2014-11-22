package reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Edgar on 14-11-22.
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("pre");
        method.invoke(target, args);
        System.out.println("post");
        return null;
    }
}
