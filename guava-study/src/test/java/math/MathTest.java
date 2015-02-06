package math;

import com.google.common.math.IntMath;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Administrator on 2015/2/6.
 */
public class MathTest {

    @Test(expected = ArithmeticException.class)
    public void testAdd() {
        //ArithmeticException
        IntMath.checkedAdd(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Assert.fail();
    }
}
