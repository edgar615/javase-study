package base;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by Administrator on 2014/12/19.
 */
public class JoinerTest {

    @Test
    public void testJoiner() {
        Joiner joiner = Joiner.on(";");
        String str = joiner.join("Edgar", "Adonis", "Leona", "Ayumi");
        System.out.println(str);
    }

    @Test
    public void testJoinerOnString() {
        Joiner joiner = Joiner.on("[---]");
        String str = joiner.join("Edgar", "Adonis", "Leona", "Ayumi");
        System.out.println(str);
    }

    @Test
    public void testJoinerArray() {
        Joiner joiner = Joiner.on(";");
        String str = joiner.join(new String[] {"Edgar", "Adonis", "Leona", "Ayumi"});
        System.out.println(str);
    }

    @Test
    public void testJoinerList() {
        Joiner joiner = Joiner.on(";");
        String str = joiner.join(Arrays.asList("Edgar", "Adonis", "Leona", "Ayumi"));
        System.out.println(str);
    }

    @Test
    public void testJoinerAppend() {
        Joiner joiner = Joiner.on(";");
        StringBuilder appender = new StringBuilder("Users：");
        StringBuilder str = joiner.appendTo(appender, Arrays.asList("Edgar", "Adonis", "Leona", "Ayumi"));
        System.out.println(str);
    }

    @Test
    public void testJoinerSkipNull() {
        Joiner joiner = Joiner.on(";");
        StringBuilder appender = new StringBuilder("Users：");
        // NullPointerException
//        String str = joiner.join(Arrays.asList("Edgar", "Adonis", null, "Ayumi"));
        String str = joiner.skipNulls().join(Arrays.asList("Edgar", "Adonis", null, "Ayumi"));
        System.out.println(str);
    }

    @Test
    public void testJoinerUseForNull() {
        Joiner joiner = Joiner.on(";").useForNull("NullValue");
        StringBuilder appender = new StringBuilder("Users：");
        String str = joiner.join(Arrays.asList("Edgar", "Adonis", null, "Ayumi"));
        System.out.println(str);
    }

    @Test
    public void testWithKeyValueSeparator() {
        Joiner.MapJoiner joiner = Joiner.on("&").withKeyValueSeparator("=");
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "Edgar");
        map.put("age", 28);
        map.put("sex", "male");
        String str = joiner.join(map);
        System.out.println(str);
    }
}
