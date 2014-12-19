joiner实例总是不可变的。用来定义joiner目标语义的配置方法总会返回一个新的joiner实例。这使得joiner实例都是线程安全的，你可以将其定义为static final常量。

### Joiner

#### 使用Char连接

<pre>
Joiner joiner = Joiner.on(";");
String str = joiner.join("Edgar", "Adonis", "Leona", "Ayumi");
System.out.println(str);
</pre>

输出：
    
    Edgar;Adonis;Leona;Ayumi

#### 使用String连接

<pre>
Joiner joiner = Joiner.on("[---]");
String str = joiner.join("Edgar", "Adonis", "Leona", "Ayumi");
System.out.println(str);
</pre>

输出:
    
    Edgar[---]Adonis[---]Leona[---]Ayumi

#### 连接数组

<pre>
Joiner joiner = Joiner.on(";");
String str = joiner.join(new String[] {"Edgar", "Adonis", "Leona", "Ayumi"});
System.out.println(str);
</pre>

输出:
    
    Edgar;Adonis;Leona;Ayumi
    
#### 连接列表

<pre>
Joiner joiner = Joiner.on(";");
String str = joiner.join(Arrays.asList("Edgar", "Adonis", "Leona", "Ayumi"));
System.out.println(str);
</pre>

输出:
    
    Edgar;Adonis;Leona;Ayumi    
    
#### appendTo

实现Appendable接口的类都可以使用appendTo连接

<pre>
Joiner joiner = Joiner.on(";");
StringBuilder appender = new StringBuilder("Users：");
StringBuilder str = joiner.appendTo(appender, Arrays.asList("Edgar", "Adonis", "Leona", "Ayumi"));
System.out.println(str);
</pre>

输出:
    
    Users：Edgar;Adonis;Leona;Ayumi   
    
#### skipNulls

使用skipNulls忽略null

<pre>
Joiner joiner = Joiner.on(";");
StringBuilder appender = new StringBuilder("Users：");
// NullPointerException
//        String str = joiner.join(Arrays.asList("Edgar", "Adonis", null, "Ayumi"));
String str = joiner.skipNulls().join(Arrays.asList("Edgar", "Adonis", null, "Ayumi"));
System.out.println(str);
</pre>

输出:
    
    Edgar;Adonis;Ayumi    
       
#### useForNull

使用useForNull使用给定的字符串替换null

<pre>
Joiner joiner = Joiner.on(";").useForNull("NullValue");
StringBuilder appender = new StringBuilder("Users：");
String str = joiner.join(Arrays.asList("Edgar", "Adonis", null, "Ayumi"));
System.out.println(str);
</pre>

输出:
    
    Edgar;Adonis;NullValue;Ayumi       
    
        
#### 连接Map
withKeyValueSeparator会返回一个内部类：MapJoiner

<pre>
Joiner.MapJoiner joiner = Joiner.on("&").withKeyValueSeparator("=");
Map<String, Object> map = Maps.newHashMap();
map.put("name", "Edgar");
map.put("age", 28);
map.put("sex", "male");
String str = joiner.join(map);
System.out.println(str);
</pre>

输出:
    
    sex=male&name=Edgar&age=28    