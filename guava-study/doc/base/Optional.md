Guava用Optional<T>表示可能为null的T类型引用。一个Optional实例可能包含非null的引用（我们称之为引用存在），也可能什么也不包括（称之为引用缺失）。它从不说包含的是null值，而是用存在或缺失来表示。但Optional从不会包含null值引用。

<pre>
Optional<Integer> optional = Optional.of(5);
Assert.assertTrue(optional.isPresent());
Assert.assertEquals(5, optional.get(), 0);
</pre>

#### Optional.of(T)
创建指定引用的Optional实例，若引用为null则快速失败

<pre>
Optional<String> optional = Optional.of("A");
optional = Optional.of(null);
Assert.fail();
</pre>

#### Optional.absent()
创建引用缺失的Optional实例

<pre>
Optional<String> optional = Optional.absent();
Assert.assertFalse(optional.isPresent());
Assert.assertNull(optional.get());
</pre>

#### Optional.fromNullable(T)
创建指定引用的Optional实例，若引用为null则表示缺失

<pre>
Optional<String> optional = Optional.fromNullable("A");
Assert.assertTrue(optional.isPresent());

optional = Optional.fromNullable(null);
Assert.assertFalse(optional.isPresent());
</pre>

#### boolean isPresent()
如果Optional包含非null的引用（引用存在），返回true

#### T get()
返回Optional所包含的引用，若引用缺失，则抛出java.lang.IllegalStateException

<pre>
Optional<String> optional = Optional.fromNullable("A");
Assert.assertTrue(optional.isPresent());
Assert.assertEquals("A", optional.get());

optional = Optional.absent();
Assert.assertFalse(optional.isPresent());
optional.get();
Assert.fail();
</pre>

#### T or(T) 	
返回Optional所包含的引用，若引用缺失，返回指定的值

<pre>
Optional<String> optional = Optional.of("A");
Assert.assertEquals("A", optional.or("B"));

optional = Optional.absent();
Assert.assertEquals("B", optional.or("B"));
</pre>

#### T orNull()
返回Optional所包含的引用，若引用缺失，返回null

<pre>
Optional<String> optional = Optional.of("A");
Assert.assertEquals("A", optional.orNull());

optional = Optional.absent();
Assert.assertNull(optional.orNull());
</pre>

#### Set<T> asSet()
返回Optional所包含引用的单例不可变集，如果引用存在，返回一个只有单一元素的集合，如果引用缺失，返回一个空集合。

<pre>
Optional<String> optional = Optional.of("A");
Set<String> set = optional.asSet();
Assert.assertEquals(1, set.size());
Assert.assertEquals("A",set.iterator().next());
</pre>