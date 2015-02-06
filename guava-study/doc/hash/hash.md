
### HashFunction
HashFunction是一个单纯的（引用透明的）、无状态的方法，它把任意的数据块映射到固定数目的位指，并且保证相同的输入一定产生相同的输出，不同的输入尽可能产生不同的输出。

### Hasher

HashFunction的实例可以提供有状态的Hasher，Hasher提供了流畅的语法把数据添加到散列运算，然后获取散列值。Hasher可以接受所有原生类型、字节数组、字节数组的片段、字符序列、特定字符集的字符序列等等，或者任何给定了Funnel实现的对象。

Hasher实现了PrimitiveSink接口，这个接口为接受原生类型流的对象定义了fluent风格的API

### Funnel

Funnel描述了如何把一个具体的对象类型分解为原生字段值，从而写入PrimitiveSink。比如，如果我们有这样一个类：

<pre>
class Person {
    final int id;
    final String firstName;
    final String lastName;
    final int birthYear;
}
</pre>

它对应的Funnel实现可能是：

<pre>
Funnel<Person> personFunnel = new Funnel<Person>() {
    @Override
    public void funnel(Person from, PrimitiveSink into) {
        into.putInt(from.getId())
                .putString(from.getFirstName(), Charsets.UTF_8)
                .putString(from.getLastName(), Charsets.UTF_8)
                .putInt(from.getBirthYear());
    }
};
</pre>

### HashCode

一旦Hasher被赋予了所有输入，就可以通过hash()方法获取HashCode实例（多次调用hash()方法的结果是不确定的）。HashCode可以通过asInt()、asLong()、asBytes()方法来做相等性检测，此外，writeBytesTo(array, offset, maxLength)把散列值的前maxLength字节写入字节数组。

### 布鲁姆过滤器[BloomFilter]
Guava散列包有一个内建的布鲁姆过滤器实现，你只要提供Funnel就可以使用它。你可以使用create(Funnel funnel, int expectedInsertions, double falsePositiveProbability)方法获取BloomFilter<T>，缺省误检率[falsePositiveProbability]为3%。BloomFilter<T>提供了boolean mightContain(T) 和void put(T)，它们的含义都不言自明了。

<pre>
BloomFilter<Person> friends = BloomFilter.create(personFunnel, 500, 0.01);
for(Person friend : friendsList) {
    friends.put(friend);
}

// 很久以后
if (friends.mightContain(dude)) {
    //dude不是朋友还运行到这里的概率为1%
    //在这儿，我们可以在做进一步精确检查的同时触发一些异步加载
}
</pre>

### Hashing类

    md5() 	
    murmur3_128()
    murmur3_32() 
    sha1()
    sha256() 
    sha512() 	
    goodFastHash(int bits) 	
    
### HashCode运算
#### HashCode combineOrdered( Iterable<HashCode>) 	
以有序方式联接散列码，如果两个散列集合用该方法联接出的散列码相同，那么散列集合的元素可能是顺序相等的
#### HashCode   combineUnordered( Iterable<HashCode>) 	
以无序方式联接散列码，如果两个散列集合用该方法联接出的散列码相同，那么散列集合的元素可能在某种排序下是相等的
#### int   consistentHash( HashCode, int buckets) 	
为给定的”桶”大小返回一致性哈希值。当”桶”增长时，该方法保证最小程度的一致性哈希值变化。详见一致性哈希。