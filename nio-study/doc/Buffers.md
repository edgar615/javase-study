Java NIO中的Buffer用于和NIO通道进行交互。
写：Buffer -> Channel
读：Channel -> Buffer

使用Buffer读写数据的步骤：
1. Write data into the Buffer
2. Call buffer.flip()
3. Read data out of the Buffer
4. Call buffer.clear() or buffer.compact()

clear()方法会清空整个缓冲区。
compact()方法只会清除已经读过的数据。任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。

<pre>
RandomAccessFile file = new RandomAccessFile("random.log", "rw");
FileChannel channel = file.getChannel();
//创建缓冲区
ByteBuffer byteBuffer = ByteBuffer.allocate(48);
//将数据写入缓冲区
int bytesRead = channel.read(byteBuffer);
while (bytesRead != -1) {
    //反转缓冲区，将Buffer从写模式切换到读模式
    byteBuffer.flip();
    while(byteBuffer.hasRemaining()){
        //一次读取一个字节
        System.out.print((char) byteBuffer.get());
    }

    byteBuffer.clear();
    bytesRead = channel.read(byteBuffer);
}
file.close();
</pre>

### Buffer Capacity, Position and Limit
- capacity

作为一个内存块，Buffer有一个固定的大小值，也叫“capacity”.你只能往里写capacity个byte、long，char等类型。一旦Buffer满了，需要将其清空（通过读数据或者清除数据）才能继续写数据往里写数据。

- position
  
当你写数据到Buffer中时，position表示当前的位置。初始的position值为0.当一个byte、long等数据写到Buffer后， position会向前移动到下一个可插入数据的Buffer单元。position最大可为capacity – 1.

当读取数据时，也是从某个特定位置读。当将Buffer从写模式切换到读模式，position会被重置为0. 当从Buffer的position处读取数据时，position向前移动到下一个可读的位置。

- limit
  
在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。 写模式下，limit等于Buffer的capacity。

当切换Buffer到读模式时， limit表示你最多能读到多少数据。因此，当切换Buffer到读模式时，limit会被设置成写模式下的position值。换句话说，你能读到之前写入的所有数据（limit被设置成已写数据的数量，这个值在写模式下就是position）

### Buffer Types
Buffer有以下类型
- ByteBuffer
- MappedByteBuffer
- CharBuffer
- DoubleBuffer
- FloatBuffer
- IntBuffer
- LongBuffer
- ShortBuffer

### Allocating a Buffer
要想获得一个Buffer对象首先要进行分配
<pre>
ByteBuffer buf = ByteBuffer.allocate(48);
</pre>
<pre>
CharBuffer buf = CharBuffer.allocate(1024);
</pre>

### Writing Data to a Buffer
向Buffer中写入数据有两种方式
- Write data from a Channel into a Buffer
- Write data into the Buffer yourself, via the buffer's put() methods.

*从Channel写入*
<pre>
 int bytesRead = channel.read(byteBuffer);
</pre>
*通过buffer.put写入*
<pre>
buf.put(127);
</pre>

### flip()
flip()将Buffer从写模式切换到读模式
调用flip()方法会将position设为0，limit设为之前写的position

### Reading Data from a Buffer
从Buffer中读取数据有两种方式
- Read data from the buffer into a channel.
- Read data from the buffer yourself, using one of the get() methods.

*从Buffer读取到Channel*
<pre>
int bytesWritten = inChannel.write(buf);
</pre>

*通过buffer.get读取*
<pre>
byte aByte = buf.get();
</pre>

### rewind()
rewind()将position设为0，可以重新读取数据

### clear()、compact()
clear()方法会清空整个缓冲区。
compact()方法只会清除已经读过的数据。任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。

### mark()、reset()
通过调用Buffer.mark()方法，可以标记Buffer中的一个特定position。之后可以通过调用Buffer.reset()方法恢复到这个position。例如：
<pre>
buffer.mark();
//call buffer.get() a couple of times, e.g. during parsing.
buffer.reset();  //set position back to mark. 
</pre>

### equals()与compareTo()方法

可以使用equals()和compareTo()方法两个Buffer。
equals()

当满足下列条件时，表示两个Buffer相等：

    有相同的类型（byte、char、int等）。
    Buffer中剩余的byte、char等的个数相等。
    Buffer中所有剩余的byte、char等都相同。

如你所见，equals只是比较Buffer的一部分，不是每一个在它里面的元素都比较。实际上，它只比较Buffer中的剩余元素。
compareTo()方法

compareTo()方法比较两个Buffer的剩余元素(byte、char等)， 如果满足下列条件，则认为一个Buffer“小于”另一个Buffer：

    第一个不相等的元素小于另一个Buffer中对应的元素 。
    所有元素都相等，但第一个Buffer比另一个先耗尽(第一个Buffer的元素个数比另一个少)。
