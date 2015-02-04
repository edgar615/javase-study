package base;

import com.google.common.base.Strings;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Administrator on 2015/2/4.
 */
public class StringsTest {

    @Test
    public void testNullToEmpty() {
        String s = Strings.nullToEmpty(null);
        Assert.assertNotNull(s);
        Assert.assertEquals("", s);
    }

    @Test
    public void testEmptyToNull() {
        String s = Strings.emptyToNull("");
        Assert.assertNull(s);
    }

    @Test
    public void testIsNullOrEmpty() {
        Assert.assertTrue(Strings.isNullOrEmpty(null));
        Assert.assertTrue(Strings.isNullOrEmpty(""));
    }

    @Test
    public void testPadStart() {
        Assert.assertEquals("007", Strings.padStart("7", 3, '0'));
        Assert.assertEquals("2010", Strings.padStart("2010", 3, '0'));
    }

    @Test
    public void testPadEnd() {
        Assert.assertEquals("4.000", Strings.padEnd("4.", 5, '0'));
        Assert.assertEquals("2010", Strings.padEnd("2010", 3, '!'));
    }

    @Test
    public void testRepeat() {
        Assert.assertEquals("HeyHeyHey", Strings.repeat("Hey", 3));
    }

    @Test
    public void testCommonPrefix() {
        String prefix = Strings.commonPrefix("Hello, World!", "Hi, World!");
        Assert.assertEquals("H", prefix);
    }

    @Test
    public void testCommonSuffix() {
        String suffix = Strings.commonSuffix("Hello, World!", "Hi, World!");
        Assert.assertEquals(", World!", suffix);
    }
}
