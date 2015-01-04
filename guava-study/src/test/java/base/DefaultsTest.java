package base;

import com.google.common.base.Defaults;
import org.junit.Test;

/**
 * Created by Administrator on 2015/1/4.
 */
public class DefaultsTest {

    @Test
    public void testDefaults() {
        System.out.println(Defaults.defaultValue(boolean.class));
        System.out.println(Defaults.defaultValue(char.class));
        System.out.println(Defaults.defaultValue(short.class));
        System.out.println(Defaults.defaultValue(byte.class));
        System.out.println(Defaults.defaultValue(int.class));
        System.out.println(Defaults.defaultValue(long.class));
        System.out.println(Defaults.defaultValue(float.class));
        System.out.println(Defaults.defaultValue(double.class));

        System.out.println(int.class);
    }
}
