package base;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Administrator on 2015/2/4.
 */
public class SuppliersTest {

    @Test
    public void testCompose() {
        Function<User, String> function = new Function<User, String>() {
            @Override
            public String apply(User input) {
                return input.getUsername();
            }
        };
        Supplier<User> user = new Supplier<User>() {
            @Override
            public User get() {
                return new User("Edgar");
            }
        };
        Supplier<String> supplier = Suppliers.compose(function, user);
        Assert.assertEquals("Edgar", supplier.get());
    }

    //    @Test
//    public void testMemoization() {
//        User user = new User("Edgar");
//        Supplier<User> supplierUser = new Supplier<User>() {
//            @Override
//            public User get() {
//                return user;
//            }
//        };
//        Supplier<User> supplier = Suppliers.memoize(supplierUser);
//        Assert.assertEquals("Edgar", supplier.get().getUsername());
//
//        user.setUsername("Leo");
//
//        Assert.assertEquals("Leo", user.getUsername());
//        Assert.assertEquals("Edgar", supplier.get().getUsername());
//        Assert.assertNotEquals("Leo", supplier.get().getUsername());
//    }
    @Test
    public void testMemoization() {
        final AtomicInteger count = new AtomicInteger(0);
        Supplier<User> user = new Supplier<User>() {
            @Override
            public User get() {
                count.incrementAndGet();
                return new User("Edgar");
            }
        };
        Supplier<User> supplier = Suppliers.memoize(user);
        Assert.assertEquals("Edgar", supplier.get().getUsername());
        Assert.assertEquals("Edgar", supplier.get().getUsername());

        Assert.assertEquals(1, count.get());

        Assert.assertSame(supplier.get(), supplier.get());
    }

    @Test
    public void testMemoizeWithExpiration() throws InterruptedException {
        final AtomicInteger count = new AtomicInteger(0);
        Supplier<User> user = new Supplier<User>() {
            @Override
            public User get() {
                count.incrementAndGet();
                return new User("Edgar");
            }
        };
        Supplier<User> supplier = Suppliers.memoizeWithExpiration(user, 1, TimeUnit.SECONDS);
        Assert.assertEquals("Edgar", supplier.get().getUsername());
        Assert.assertEquals("Edgar", supplier.get().getUsername());
        Assert.assertEquals(1, count.get());

        TimeUnit.SECONDS.sleep(2);

        Assert.assertEquals("Edgar", supplier.get().getUsername());
        Assert.assertEquals("Edgar", supplier.get().getUsername());
        Assert.assertEquals(2, count.get());

    }

    @Test
    public void testOfInstance() {
        User user = new User("Edgar");
        Supplier<User> supplier = Suppliers.ofInstance(user);
        Assert.assertSame(user, supplier.get());
    }

    @Test
    public void testSupplierFunction() {
        User user = new User("Edgar");
        Function<Supplier<User>, User>  supplier = Suppliers.supplierFunction();
        User user2 = supplier.apply(Suppliers.ofInstance(user));
        Assert.assertSame(user, user2);
    }

    class User {
        private String username;

        User(String username) {
            this.username = username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }
}
