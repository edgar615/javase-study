package base;

import com.google.common.base.CaseFormat;
import org.junit.Test;

/**
 * Created by Administrator on 2014/12/23.
 */
public class CaseFormatTest {

    @Test
    public void testLowerHyphen() {
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, "CONSTANT_NAME"));
    }

    @Test
    public void testLowerUnderscore() {
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, "CONSTANT_NAME"));
    }

    @Test
    public void testLowerCamel() {
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME"));
    }

    @Test
    public void testUpperCamel() {
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "CONSTANT_NAME"));
    }

    @Test
    public void testUpperUnderscore() {
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, "constantName"));
    }

    @Test
    public void testUpperCamelToLowerCamel() {
        System.out.println(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, "ConstantName"));
    }
}
