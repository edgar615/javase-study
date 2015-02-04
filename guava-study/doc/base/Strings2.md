#### nullToEmpty
null转换为""

<pre>
String s = Strings.nullToEmpty(null);
Assert.assertNotNull(s);
Assert.assertEquals("", s);
</pre>

#### emptyToNull
""转换为null

<pre>
String s = Strings.emptyToNull("");
Assert.assertNull(s);
</pre>

#### isNullOrEmpty
""或null

<pre>
Assert.assertTrue(Strings.isNullOrEmpty(null));
Assert.assertTrue(Strings.isNullOrEmpty(""));
</pre>

#### padStart
不足位数在前面补齐指定的字符

<pre>
Assert.assertEquals("007", Strings.padStart("7", 3, '0'));
Assert.assertEquals("2010", Strings.padStart("2010", 3, '0'));
</pre>

#### padEnd
不足位数在后面补齐指定的字符

<pre>
Assert.assertEquals("4.000", Strings.padEnd("4.", 5, '0'));
Assert.assertEquals("2010", Strings.padEnd("2010", 3, '!'));
</pre>

#### repeat
重复叠加字符串

<pre>
Assert.assertEquals("HeyHeyHey", Strings.repeat("Hey", 3));
</pre>

#### commonPrefix
提取两个字符串相同的前缀

<pre>
String prefix = Strings.commonPrefix("Hello, World!", "Hi, World!");
Assert.assertEquals("H", prefix);
</pre>

#### commonSuffix
提取两个字符串相同的后缀

<pre>
String suffix = Strings.commonSuffix("Hello, World!", "Hi, World!");
Assert.assertEquals(", World!", suffix);
</pre>

#### validSurrogatePairAt