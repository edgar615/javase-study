### Splitter

#### 按单个字符拆分字符串

<pre>
    @Test
    public void testSplit() {
        final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
        Iterable<String> splits = Splitter.on(',').split(input);
        printWords(splits);
    }
    
    private static void printWords(Iterable<String> words) {
        for (String word : words) {
            System.out.printf("[%s]\n", word);
        }
    }
</pre>

输出：
    
    [Alpha]
    [ Bravo]
    []
    [Charlie ]
    [Delta]
    [Echo]

#### 按字符串拆分字符串

<pre>
final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
Iterable<String> splits = Splitter.on(",").split(input);
printWords(splits);
</pre>

输出：
    
    [Alpha]
    [ Bravo]
    []
    [Charlie ]
    [Delta]
    [Echo]
    
#### 按字符匹配器拆分字符串

<pre>
final String input = "Alpha, Bravo,,Charlie ;Delta;Echo";
Iterable<String> splits = Splitter.on(CharMatcher.anyOf(",;")).split(input);
printWords(splits);
</pre>

输出：
    
    [Alpha]
    [ Bravo]
    []
    [Charlie ]
    [Delta]
    [Echo]
    
#### 按正则表达式拆分字符串

<pre>
final String input = "Alpha, Bravo,,Charlie ;Delta;Echo";
Iterable<String> splits = Splitter.on(Pattern.compile(";|,")).split(input);
printWords(splits);
</pre>

输出：
    
    [Alpha]
    [ Bravo]
    []
    [Charlie ]
    [Delta]
    [Echo]

#### 按固定长度拆分字符串
最后一段可能比给定长度短，但不会为空。

<pre>
final String input = "Alpha, Bravo,,Charlie ;Delta;Echo";
Iterable<String> splits = Splitter.fixedLength(5).split(input);
printWords(splits);
</pre>

输出：
    
    [Alpha]
    [, Bra]
    [vo,,C]
    [harli]
    [e ;De]
    [lta;E]
    [cho]  
          
#### omitEmptyStrings
从结果中自动忽略空字符串

<pre>
final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
Iterable<String> splits = Splitter.on(",").omitEmptyStrings().split(input);
printWords(splits);
</pre>

输出：
    
    [Alpha]
    [ Bravo]
    [Charlie ]
    [Delta]
    [Echo]
      
#### trimResults
移除结果字符串的前导空白和尾部空白

<pre>
final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
Iterable<String> splits = Splitter.on(",").trimResults().split(input);
printWords(splits);
</pre>

输出：
    
    [Alpha]
    [Bravo]
    []
    [Charlie]
    [Delta]
    [Echo]
    
#### trimResults
根据字符匹配器，移除结果字符串的前导匹配字符和尾部匹配字符

<pre>
final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
Iterable<String> splits = Splitter.on(",").trimResults(CharMatcher.anyOf("ABCabc")).split(input);
printWords(splits);
</pre>

输出：
    
    [lph]
    [ Bravo]
    []
    [harlie ]
    [Delt]
    [Echo]
    
#### limit
限制拆分出的字符串数量

<pre>
final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
Iterable<String> splits = Splitter.on(",").omitEmptyStrings().limit(3).split(input);
printWords(splits);
</pre>

输出：
    
    [Alpha]
    [ Bravo]
    [Charlie ,Delta,Echo]  
    
    
#### splitToList
拆分为列表

<pre>
final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
List<String> splits = Splitter.on(',').omitEmptyStrings().splitToList(input);
printWords(splits);
</pre>

输出：
    
    [Alpha]
    [ Bravo]
    [Charlie ,Delta,Echo]  
    
#### 拆分为Map
withKeyValueSeparator会返回一个内部类：MapSplitter

<pre>
final String input = "A=>B , C=>D";
Map<String, String> splits = Splitter.on(',').trimResults().withKeyValueSeparator("=>").split(input);
System.out.println(splits);
</pre>

输出:
    
    {A=B, C=D}   