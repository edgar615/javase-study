package op;

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalTest {
    /**
     * of后面接给optional设置的值 但是不能为空 如果为空会报空指针异常
     *
     * @throws
     * @Title: ofTest
     * @Description: TODO
     * @param:
     * @return: void
     */
//  @Test
    public void ofTest() {
        Optional<String> optional = Optional.of("123");
        System.out.println(optional.get());
        try {
            optional = Optional.of(null);
            System.out.println(optional.get());  //get方法是获取optional的值 类型取决于声明的时候
        } catch (NullPointerException e) {
            System.out.println("空指针异常");
        }
    }

    /**
     * ofNullable 和of类似 但是ofNullable可以设置null值  如果是Null值得话取值会报NoSuchElementException 异常
     *
     * @throws
     * @Title: ofNullableTest
     * @Description: TODO
     * @param:
     * @return: void
     */
//  @Test
    public void ofNullableTest() {
        Optional<String> optional = Optional.ofNullable("123");
        System.out.println(optional.get());
        try {
            optional = Optional.ofNullable(null);
            System.out.println(optional.get());
        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException 异常");
        }
    }

    /**
     * ifPresent用来判断optional中有没有值存在 如果有则为真
     *
     * @throws
     * @Title: isPresentTest
     * @Description: TODO
     * @param:
     * @return: void
     */
//  @Test
    public void isPresentTest() {
        Optional<String> optional = Optional.ofNullable(null);
        if (optional.isPresent()) {
            System.out.println(optional.get());
        } else {
            System.out.println("值为空");
        }
    }

    /**
     * ifPresent和isPresent类似 只不过它支持λ表达式
     *
     * @throws
     * @Title: ifPresentTest
     * @Description: TODO
     * @param:
     * @return: void
     */
//  @Test
    public void ifPresentTest() {
        Optional<String> optional = Optional.ofNullable("123");
        optional.ifPresent(var -> {
            System.out.println(var);
        });
    }

    /**
     * orElse方法，如果值为空的话会用参数中的值去替换 即设置默认值
     *
     * @throws
     * @Title: orElseTest
     * @Description: TODO
     * @param:
     * @return: void
     */
//  @Test
    public void orElseTest() {
        Optional<String> optional = Optional.ofNullable("123");
        System.out.println(optional.orElse("有没有"));
        optional = Optional.ofNullable(null);
        System.out.println(optional.orElse("有没有"));
    }

    /**
     * orElseGet方法 和orElse类似 不过此方法接受Supplier接口的实现用来生成默认值
     *
     * @throws
     * @Title: orElseGetTest
     * @Description: TODO
     * @param:
     * @return: void
     */
//  @Test
    public void orElseGetTest() {
        Optional<String> optional = Optional.ofNullable("123");
        System.out.println(optional.orElseGet(() -> "123456"));
        optional = Optional.ofNullable(null);
        System.out.println(optional.orElseGet(() -> "123456"));
    }

    /**
     * map方法  如果有值则会对值进行mapping中的处理 处理结果存在则创建并返回Optional类型的结果 否则返回空
     *
     * @throws
     * @Title: mapTest
     * @Description: TODO
     * @param:
     * @return: void
     */
//  @Test
    public void mapTest() {
        Optional<String> optional = Optional.ofNullable("abc");
        System.out.println(optional.map(var -> var.toUpperCase()).get());
    }

    /**
     * flatMap和map类似 只不过mapping中必须返回Option类型的数据
     *
     * @throws
     * @Title: flatMapTest
     * @Description: TODO
     * @param:
     * @return: void
     */
//  @Test
    public void flatMapTest() {
        Optional<String> optional = Optional.ofNullable("abc");
        System.out.println(optional.flatMap(var -> Optional.of(var.toUpperCase())).get());
    }

    /**
     * filter对optional进行过滤,mapping中为过滤的条件  如果不满足条件 返回一个为空的Optional
     *
     * @throws
     * @Title: filterTest
     * @Description: TODO
     * @param:
     * @return: void
     */
//    @Test
    public void filterTest() {
        try {
            Optional<String> optional = Optional.ofNullable("一二三四五六七八");
            System.out.println(optional.filter(var -> var.length() > 6).get());
            System.out.println(optional.filter(var -> var.length() < 6).get());
        } catch (NoSuchElementException e) {
            System.out.println("optional的值为空");
        }
    }
}