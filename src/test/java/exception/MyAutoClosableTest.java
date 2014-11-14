package exception;

import org.junit.Test;

/**
 * Created by Administrator on 2014/11/14.
 */
public class MyAutoClosableTest {

    @Test
    public void testAutoClosable() throws Exception {
        try (MyAutoClosable myAutoClosable = new MyAutoClosable()) {
            myAutoClosable.doIt();
        }
    }

    public static void main(String[] args) {
        throw new RuntimeException("new", new NullPointerException("null"));
    }
}
