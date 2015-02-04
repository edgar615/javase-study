Supplier接口提供一个单一类型的对象。

### Suppliers

#### compose

<pre>
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
</pre>

#### memoize

<pre>
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
</pre>

#### memoizeWithExpiration

<pre>
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
Assert.assertEquals(1, count.get());;

TimeUnit.SECONDS.sleep(2);

Assert.assertEquals("Edgar", supplier.get().getUsername());
Assert.assertEquals("Edgar", supplier.get().getUsername());
Assert.assertEquals(2, count.get());
</pre>

#### ofInstance

<pre>
User user = new User("Edgar");
Supplier<User> supplier = Suppliers.ofInstance(user);
Assert.assertSame(user, supplier.get());
</pre>

#### synchronizedSupplier

<pre>
</pre>

#### supplierFunction

<pre>
</pre>