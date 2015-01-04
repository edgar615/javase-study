package base;

import com.google.common.base.Enums;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2015/1/4.
 */
public class EnumsTest {

    enum Sex {
        MALE,
        FEMAILE,
        UNKONW
    }

    @Test
    public void testGetField() {
        Field field = Enums.getField(Sex.MALE);
        System.out.println(field.getName());
    }
    @Test
    public void testGetIfPresent() {
        System.out.println(Enums.getIfPresent(Sex.class, "boy").or(Sex.FEMAILE));
    }

    @Test
    public void testConvert() {
        Sex sex = Enums.stringConverter(Sex.class).convert("MALE");
        System.out.println(sex);

        String s = Enums.stringConverter(Sex.class).reverse().convert(Sex.FEMAILE);
        System.out.println(s);

    }
}
