#### firstNonNull

<pre>
List<Integer> first = ImmutableList.of(1, 2);
List<Integer> second = null;
List<Integer> result = MoreObjects.firstNonNull(first, second);
Assert.assertSame(first, result);
result = MoreObjects.firstNonNull(second, first);
Assert.assertSame(first, result);

MoreObjects.firstNonNull(null, null);
Assert.fail();
</pre>

#### toStringHelper

<pre>
Assert.assertEquals("ObjString{x=1}", MoreObjects.toStringHelper("ObjString").add("x", 1).toString());
Assert.assertEquals("Object{x=1}", MoreObjects.toStringHelper(Object.class).add("x", 1).toString());
Assert.assertEquals("Object{x=1}", MoreObjects.toStringHelper(new Object()).add("x", 1).toString());
Assert.assertEquals("Object{x=1, y=str}", MoreObjects.toStringHelper(new Object()).add("x", 1).add("y", "str").toString());
Assert.assertEquals("Object{x=1, y=null}", MoreObjects.toStringHelper(new Object()).add("x", 1).add("y", null).toString());
Assert.assertEquals("Object{x=1}", MoreObjects.toStringHelper(new Object()).omitNullValues().add("x", 1).add("y", null).toString());
</pre>

#### equal

<pre>
Assert.assertTrue(Objects.equal("a", "a"));
Assert.assertFalse(Objects.equal("a", "A"));
</pre>

#### hashCode

<pre>
System.out.println(Objects.hashCode(new Object()));
System.out.println(Objects.hashCode(new Object(), new Object()));
</pre>
