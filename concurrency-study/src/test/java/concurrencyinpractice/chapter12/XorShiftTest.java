package concurrencyinpractice.chapter12;

import org.junit.Test;

/**
 * Created by Administrator on 2014/12/16.
 */
public class XorShiftTest {
    @Test
    public void testRandInt() {
        XorShift xorShift = new XorShift();
        int random = xorShift.next();
        System.out.println(random);
    }
}
