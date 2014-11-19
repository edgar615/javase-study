
### 使用Reader读取char[]
The CharArrayReader class enables you to read the contents of a char array as a character stream
> CharArrayReader实现一个可用作字符输入流的字符缓冲区。 
- read() 
> 读取单个字符。
<pre>
char[] chars = "The CharArrayReader class enables you to read the contents of a char array as a character stream".toCharArray();
CharArrayReader reader = new CharArrayReader(chars);
int data = reader.read();
while (data != -1) {
    System.out.print((char) data);
    data = reader.read();
}   
</pre>
输出：The CharArrayReader class enables you to read the contents of a char array as a character stream

- read(char[])
> 将字符读入数组。在某个输入可用、发生 I/O 错误或者已到达流的末尾前，此方法一直阻塞。
 

<pre>
char[] chars = "The CharArrayReader class enables you to read the contents of a char array as a character stream".toCharArray();
CharArrayReader reader = new CharArrayReader(chars);
int length = 5;
char[] b = new char[length];
int readLength = reader.read(b);
while (readLength != -1) {
    System.out.print(new String(b, 0, readLength));
    readLength = reader.read(b);
}
</pre> 
输出：The CharArrayReader class enables you to read the contents of a char array as a character stream

- read(char[], off, len)
>  将字符读入数组的某一部分。 
>    
<pre>
char[] chars = "The CharArrayReader class enables you to read the contents of a char array as a character stream".toCharArray();
CharArrayReader reader = new CharArrayReader(chars);
int length = 5;
char[] b = new char[length];
int readLength = reader.read(b, 0, length);
while (readLength != -1) {
    System.out.print(new String(b, 0, readLength));
    readLength = reader.read(b, 0, length);
}
</pre>
输出：The CharArrayReader class enables you to read the contents of a char array as a character stream

- CharArrayReader(char[] buf, int offset, int length)  
<pre>
char[] chars = "The CharArrayReader class enables you to read the contents of a char array as a character stream".toCharArray();
CharArrayReader reader = new CharArrayReader(chars, 0, 5);
int data = reader.read();
while (data != -1) {
    System.out.print((char) data);
    data = reader.read();
}
</pre>
输出：The C

### 使用Writer写入char[]
The CharArrayWriter makes it possible to write characters to a Writer and convert the written characters into a char array.
> 此类实现一个可用作 Writer 的字符缓冲区。缓冲区会随向流中写入数据而自动增长。可使用 toCharArray() 和 toString() 获取数据。
- write(int)
> 将指定的字节写入此 byte 数组输出流。 
<pre>
char[] chars = "The CharArrayWriter makes it possible to write characters to a Writer and convert the written characters into a char array.".toCharArray();
CharArrayWriter out = new CharArrayWriter();
for (int i = 0; i < chars.length; i ++) {
    out.write(chars[i]);
}
System.out.println(out.toString());
System.out.println(new String(out.toCharArray()));
</pre>
输出：The CharArrayWriter makes it possible to write characters to a Writer and convert the written characters into a char array.

- write(char[])
> 写入字符数组。 
<pre>
char[] chars = "The CharArrayWriter makes it possible to write characters to a Writer and convert the written characters into a char array.".toCharArray();
CharArrayWriter writer = new CharArrayWriter();
writer.write(chars);
System.out.println(writer.toString());
System.out.println(new String(writer.toCharArray()));
</pre>
输出：The CharArrayWriter makes it possible to write characters to a Writer and convert the written characters into a char array.

- write(byte[], off, len)
> 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此输出流。 write(b, off, len) 的常规协定是：将数组 b 中的某些字节按顺序写入输出流；元素 b[off] 是此操作写入的第一个字节， b[off+len-1] 是此操作写入的最后一个字节。 
<pre>
char[] chars = "The CharArrayWriter makes it possible to write characters to a Writer and convert the written characters into a char array.".toCharArray();
CharArrayWriter writer = new CharArrayWriter();
writer.write(chars, 0, 5);
System.out.println(writer.toString());
System.out.println(new String(writer.toCharArray()));
</pre>
输出:The C
