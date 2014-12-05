BlockingDeque
 支持两个附加操作的 Queue，这两个操作是：获取元素时等待双端队列变为非空；存储元素时等待双端队列中的空间变得可用。

BlockingDeque 方法有四种形式，使用不同的方式处理无法立即满足但在将来某一时刻可能满足的操作：第一种方式抛出异常；第二种返回一个特殊值（null 或 false，具体取决于操作）；第三种无限期阻塞当前线程，直至操作成功；第四种只阻塞给定的最大时间，然后放弃。下表中总结了这些方法：

<table>
    <tr>
        <th colspan="5">第一个元素（头部）</th>
    </tr>
     <tr>
        <td></td>
        <td>抛出异常</td>
        <td>特殊值</td>
        <td>阻塞</td>
        <td>超时</td>
    </tr>
    <tr>
        <td>插入</td>
        <td>addFirst(e)</td>
        <td>offerFirst(e)</td>
        <td>putFirst(e) </td>
        <td>offerFirst(e, time, unit)</td>
    </tr>
    <tr>
        <td>移除</td>
        <td>removeFirst()</td>
        <td>pollFirst()</td>
        <td>takeFirst()</td>
        <td>pollFirst(time, unit)</td>
    </tr>
    <tr>
        <td>检查</td>
        <td>getFirst()</td>
        <td>peekFirst()</td>
        <td>不适用</td>
        <td>不适用</td>
    </tr>
    <tr>
            <th colspan="5">最后一个元素（尾部）</th>
        </tr>
         <tr>
            <td></td>
            <td>抛出异常</td>
            <td>特殊值</td>
            <td>阻塞</td>
            <td>超时</td>
        </tr>
        <tr>
            <td>插入</td>
            <td>addLast(e)</td>
            <td>offerLast(e)</td>
            <td>putLast(e)</td>
            <td>offerLast(e, time, unit)</td>
        </tr>
        <tr>
            <td>移除</td>
            <td>removeLast()</td>
            <td>pollLast()</td>
            <td>takeLast()</td>
            <td>pollLast(time, unit)</td>
        </tr>
        <tr>
            <td>检查</td>
            <td>getLast()</td>
            <td>peekLast()</td>
            <td>不适用</td>
            <td>不适用</td>
        </tr>
</table>
 
BlockingDeque 实现可以直接用作 FIFO BlockingQueue。继承自 BlockingQueue 接口的方法精确地等效于下表中描述的 BlockingDeque 方法：
 	 	 	 
<table>
    <tr>
        <th>BlockingQueue 方法</th>
        <th>等效的 BlockingDeque 方法</th>
    </tr>
    <tr>
        <th colspan="2">插入</th>
    </tr>
    <tr>
        <td>add(e)</td>
        <td>addLast(e)</td>
    </tr>
    <tr>
        <td>offer(e)</td>
        <td>offerLast(e)</td>
    </tr>
     <tr>
        <td>put(e)</td>
        <td>putLast(e)</td>
    </tr>
    <tr>
        <td>offer(e, time, unit)</td>
        <td>offerLast(e, time, unit)</td>
    </tr>
    <tr>
        <th colspan="2">移除</th>
    </tr>
    <tr>
        <td>remove() </td>
        <td>removeFirst()</td>
    </tr>
    <tr>
        <td>poll()</td>
        <td>pollFirst()</td>
    </tr>
     <tr>
        <td>take()</td>
        <td>takeFirst()</td>
    </tr>
    <tr>
        <td>poll(time, unit)</td>
        <td>pollFirst(time, unit)</td>
    </tr>
    <tr>
        <th colspan="2">检查</th>
    </tr>
    <tr>
        <td>element() </td>
        <td>getFirst()</td>
    </tr>
    <tr>
        <td>peek()</td>
        <td>peekFirst()</td>
    </tr>
</table> 	 

JDK提供的实现类

- LinkedBlockingDeque

	
 	
	
 	
 	
 	
	 	 
	 	  	
          	
          	
          	