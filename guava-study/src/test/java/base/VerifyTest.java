package base;

import com.google.common.base.Throwables;
import com.google.common.base.Verify;
import com.google.common.base.VerifyException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Administrator on 2015/2/4.
 */
public class VerifyTest {

    @Test
    public void testVerify() {
        try {
            Verify.verify(1 > 1);
        } catch (VerifyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testVerify2() {
        try {
            Verify.verify(1 > 1, "error");
        } catch (VerifyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testVerify3() {
        try {
            Verify.verify(1 > 1, "error: %s", "1");
        } catch (VerifyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testVerifyNotNull() {
        try {
            Verify.verifyNotNull(null);
        } catch (VerifyException e) {
            e.printStackTrace();
        }
    }
}
