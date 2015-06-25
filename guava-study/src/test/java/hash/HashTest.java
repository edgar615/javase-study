package hash;

import com.google.common.base.Charsets;
import com.google.common.hash.*;
import org.junit.Test;

/**
 * Created by Administrator on 2015/2/6.
 */
public class HashTest {

    @Test
    public void testHash() {
        Funnel<Person> personFunnel = new Funnel<Person>() {
            @Override
            public void funnel(Person from, PrimitiveSink into) {
                into.putInt(from.getId())
                        .putString(from.getFirstName(), Charsets.UTF_8)
                        .putString(from.getLastName(), Charsets.UTF_8)
                        .putInt(from.getBirthYear());
            }
        };
        Person person = new Person(1, "Edgar", "Zhang", 22);
        HashFunction hf = Hashing.md5();
        HashCode hc = hf.newHasher().putInt(person.getId()).putString(person.getFirstName(), Charsets.UTF_8)
                .hash();
        System.out.println(hc);

    }

    @Test
    public void testHash2() {
        Funnel<Person> personFunnel = new Funnel<Person>() {
            @Override
            public void funnel(Person from, PrimitiveSink into) {
                into.putInt(from.getId())
                        .putString(from.getFirstName(), Charsets.UTF_8)
                        .putString(from.getLastName(), Charsets.UTF_8)
                        .putInt(from.getBirthYear());
            }
        };
        Person person = new Person(1, "Edgar", "Zhang", 22);
        HashFunction hf = Hashing.sha1();
        HashCode hc = hf.newHasher().putInt(person.getId()).putString(person.getFirstName(), Charsets.UTF_8)
                .hash();
        System.out.println(hc);


    }
}
