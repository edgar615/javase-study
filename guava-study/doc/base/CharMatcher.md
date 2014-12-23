你可以认为一个CharMatcher实例代表着某一类字符，如数字或空白字符。事实上来说，CharMatcher实例就是对字符的布尔判断——CharMatcher确实也实现了Predicate<Character>——但类似”所有空白字符”或”所有小写字母”的需求太普遍了

注：CharMatcher只处理char类型代表的字符；它不能理解0x10000到0x10FFFF的Unicode 增补字符。这些逻辑字符以代理对[surrogate pairs]的形式编码进字符串，而CharMatcher只能将这种逻辑字符看待成两个独立的字符。

### 获取字符匹配器

- BREAKING_WHITESPACE
- ASCII
- DIGIT
- JAVA_DIGIT
- JAVA_LETTER
- JAVA_LETTER_OR_DIGIT
- JAVA_UPPER_CASE
- JAVA_LOWER_CASE
- JAVA_ISO_CONTROL
- INVISIBLE 
    
    Determines whether a character is invisible; that is, if its Unicode category is any of
     SPACE_SEPARATOR, LINE_SEPARATOR, PARAGRAPH_SEPARATOR, CONTROL, FORMAT, SURROGATE, and
     PRIVATE_USE according to ICU4J.
     
- SINGLE_WIDTH
- WHITESPACE
- WHITESPACE_TABLE
- WHITESPACE_SHIFT
- WHITESPACE_MULTIPLIER
- ANY

#### anyOf

<pre>
CharMatcher.anyOf("aeiou").matches('a');//true
CharMatcher.anyOf("aeiou").matches('b');//false
</pre>

#### is

<pre>
CharMatcher.is('a').matches('a')
</pre>

#### isNot

<pre>
CharMatcher.isNot('a').matches('a')
</pre>

#### inRange

<pre>
CharMatcher.inRange('b', 'e').matches('a')
</pre>

#### and

<pre>
CharMatcher.is('a').and(CharMatcher.inRange('b', 'e')).matches('a')
</pre>

#### or

<pre>
CharMatcher.is('a').or(CharMatcher.inRange('b', 'e')).matches('a')
</pre>

#### negate

<pre>
CharMatcher.anyOf("aeiou").negate().matches('a')
//false
</pre>

#### noneOf
等同于anyOf("aeiou").negate()

<pre>
CharMatcher.noneOf("aeiou").matches('a')
//false
</pre>

### 使用字符匹配器的方法
#### collapseFrom
把每组连续的匹配字符替换为特定字符。如WHITESPACE.collapseFrom(string, ‘ ‘)把字符串中的连续空白字符替换为单个空格。

<pre>
CharMatcher.anyOf("aeiou").collapseFrom("Hello Guava", '-')
//H-ll- G-v-
</pre>

#### countIn
Returns the number of matching characters found in a character sequence.

<pre>
CharMatcher.anyOf("aeiou").countIn("Hello Guava")
//5
</pre>

#### indexIn
 Returns the index of the first matching character in a character sequence

<pre>
CharMatcher.anyOf("aeiou").indexIn("Hello Guava")
//1
CharMatcher.anyOf("aeiou").indexIn("Hello Guava", 5)
//7
</pre>

#### lastIndexIn

<pre>
CharMatcher.anyOf("aeiou").lastIndexIn("Hello Guava")
//10
</pre>

#### matches

<pre>
CharMatcher.anyOf("aeiou").matches('a')
//true
</pre>

#### matchesAllOf

<pre>
CharMatcher.anyOf("aeiou").matchesAllOf("aaeeoouuii")
//true
</pre>

#### matchesAnyOf

<pre>
CharMatcher.anyOf("aeiou").matchesAnyOf("abcd")
//true
</pre>

#### matchesNoneOf

<pre>
CharMatcher.anyOf("aeiou").matchesNoneOf("bcd")
//true
</pre>

#### removeFrom
 	从字符序列中移除所有匹配字符。
 	
<pre>
CharMatcher.anyOf("aeiou").removeFrom("Hello Guava")
//Hll Gv
</pre>

#### replaceFrom
 	从字符序列中移除所有匹配字符。
 	
<pre>
CharMatcher.anyOf("aeiou").replaceFrom("Hello Guava", '-')
//H-ll- G--v-
CharMatcher.anyOf("aeiou").replaceFrom("Hello Guava", ";-")
//H;-ll;- G;-;-v;-
</pre>

#### retainFrom
在字符序列中保留匹配字符，移除其他字符。

<pre>
CharMatcher.anyOf("aeiou").retainFrom("Hello Guava")
//eouaa
</pre>

#### trimAndCollapseFrom
把每组连续的匹配字符替换为特定字符后移除字符序列的前导匹配字符和尾部匹配字符

<pre>
CharMatcher.anyOf("aeiou").trimAndCollapseFrom("Hello Guava", '-')
//H-ll- G-v
</pre>

#### trimFrom
移除字符序列的前导匹配字符和尾部匹配字符

<pre>
CharMatcher.anyOf("aeiou").trimFrom("Hello Guava")
//Hello Guav
</pre>

#### trimLeadingFrom

<pre>
CharMatcher.anyOf("ab").trimLeadingFrom("abacatbab")
//catbab
</pre>

#### trimTrailingFrom

<pre>
CharMatcher.anyOf("ab").trimTrailingFrom("abacatbab")
//abacat
</pre>

#### forPredicate