## Netty from the ground up
netty的核心类

- Bootstrap or ServerBootstrap
- EventLoop
- EventLoopGroup
- ChannelPipeline
- Channel
- Future or ChannelFuture
- ChannelInitializer
- ChannelHandler

### Netty Crash Course 
一个Netty程序是从一个Bootstrap类开始，Bootstrap类用来构建Netty并提供了设置和启动Netty的方法。
 
Netty使用Handler来允许多个协议和不同的方式处理数据，Handler用来处理一个特定的事件或者一组事件。       

ChannelInitializer
When Netty connects a client or binds a server it needs to know how to process messages 
that  are  sent  or  received.  This  is  also  done  via  different  types  of  handlers  but  to  configure 
these  handlers  Netty  has  what  is  known  as  an  ChannelInitializer.  The  role  of  the 
ChannelInitializer  is  to  add  ChannelHandler  implementations  to  whats  called  the 
ChannelPipeline.  As  you  send and  receive messages,  these handlers will determine what 
happens to the messages. An ChannelInitializer is also itself a ChannelHandler which 
automatically  removes  itself  from  the  ChannelPipeline  after  it  has  added  the  other 
handlers. 
ChannelPipeline

All  Netty  applications  are  based  on  what  is  called  a  ChannelPipeline.  The 
ChannelPipeline  is  closely  related  to  whats  known  as  the  EventLoop  and 
EventLoopGroup because all three of them are related to events or event handling. 

An EventLoops purpose  in  the application  is  to process IO operations  for  a Channel. A 
single EventLoop will typically handle events for multiple Channels. The EventLoopGroup
itself may contain more then one EventLoop and can be used to obtain an EventLoop.  

**ChannelFutures**

All IO operations in Netty are performed asynchronously. So when you connect to a host for 
example,  this  is  done  asynchronously  by  default.  The  same  is  true  when  you  write/send  a 
message.  This means  the  operation may  not  be  performed  directly  but  picked  up  later  for 
execution. Because of this you cant know if an operation was successful or not after it returns, 
but need to be able to check later for success or have some kind of ways to register a listener 
which is notified. To rectify this, Netty uses Futures and ChannelFutures. This future can be used  
to  register  a  listener,  which  will  be  notified  when  an  operation  has  either  failed  or 
completed successfully.  
 
 ### Channels, Events and Input/Output (IO) 
 Netty  is  a  non-blocking,  event  driven,  networking  framework. 
 
**The EventLoop is always bound to a single Thread that never changed during its life time.**
 
 When a channel is registered, Netty binds that channel to a single EventLoop (and so to 
 a single thread) for the lifetime of that Channel. This is why your application doesnt need to 
 synchronize  on  Netty  IO  operations  because  all  IO  for  a  given  Channel  will  always  be 
 performed by the same thread. 
 
### Bootstrapping: What and Why 
  there are two types of Bootstraps, one typically used 
 for  clients,  but  is  also  used  for  DatagramChannel  (simply  called  Bootstrap)  and  one  for 
 servers  (aptly  named  ServerBootstrap). 
 
 *Similarities and differences between the two types of Bootstraps *
 
 <table>
    <tr>
        <th>Similarities</th>
        <th>Bootstrap</th>
        <th>ServerBootstrap</th>
    </tr>
    <tr>
        <td>Responsible for </td>
        <td> Connects to a remote host and  port </td>
        <td> Binds to local port </td>
    </tr>
    <tr>
        <td> Number of EventLoopGroups</td>
        <td>1</td>
        <td>2</td>
    </tr>        
 </table>
   
### Channel Handlers and Data Flow 
#### Piecing it together, ChannelPipeline and handlers 
the ChannelPipeline is an arrangement of a series of ChannelHandler. 
Each ChannelHandler performs its actions on  the  data (if  it  can  handle  it,  for  example  inbound  data  can  only  be  handled  by ChannelInboundHandlers)
then  may  pass  the  transformed  data  to  the  next ChannelHandler in the ChannelPipeline, until no more ChannelHandler remain.
  
This means there are two ways of sending messages in Netty. You can write directly to the channel or write to the ChannelHandlerContext object. The main difference between
the two is that writing to the channel directly causes the message to start from the tail of the ChannelPipeline where as writing  to  the context object causes  the message  to start  from the next handler in the ChannelPipeline.

### Encoders, Decoders and Domain Logic: A Closer Look at Handlers 
#### Encoders, decoders 
#### Domain logic 