package reflection;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by Administrator on 2015/2/6.
 */
public class TypeTokenTest {

    @Test
    public void testStart() {
        //获取一个基本的、原始类的TypeToken
        TypeToken<String> stringTok = TypeToken.of(String.class);
        TypeToken<Integer> intTok = TypeToken.of(Integer.class);

        //为获得一个含有泛型的类型的TypeToken —— 当你知道在编译时的泛型参数类型 —— 你使用一个空的匿名内部类：
        TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {
        };

        //或者你想故意指向一个通配符类型
        TypeToken<Map<?, ?>> mapTok = new TypeToken<Map<?, ?>>() {
        };
    }

    @Test
    public void testMap() {
        TypeToken<Map<String, Queue<String>>> mapToken = mapToken(
                TypeToken.of(String.class),
                new TypeToken<Queue<String>>() {}
        );
        TypeToken<Map<Integer, Queue<String>>> complexToken = mapToken(
                TypeToken.of(Integer.class),
                new TypeToken<Queue<String>>() {}
        );
    }

    static <K, V> TypeToken<Map<K, V>> mapToken(TypeToken<K> keyToken, TypeToken<V> valueToken) {
        return new TypeToken<Map<K, V>>() {
        }
                .where(new TypeParameter<K>() {
                }, keyToken)
                .where(new TypeParameter<V>() {
                }, valueToken);
    }
}
