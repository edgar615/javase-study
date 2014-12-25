### Writing an Echo server 
Writing a Netty server consists of two main parts:  
 BootstrappingConfigure server features, such as the threading and port.  
 Implementing  the  server handlerBuild out  the  component  that  contains  the business 
logic,  which  determines  what  should  happen  when  a  connection  is made  and  data  is 
received. 

#### Bootstrapping the server 

You create a ServerBootstrap instance to bootstrap the server and bind it later. 
 You create and assign the NioEventLoopGroup instances to handle event processing, 
such as accepting new connections, receiving data, writing data, and so on. 
 You specify the local InetSocketAddress to which the server binds. 
 You set up a childHandler that executes for every accepted connection. 
 After everything is set up, you call the ServerBootstrap.bind() method to bind the 
server. 

#### Implementing the server/business logic

### Writing an echo client 
The clients role includes the following tasks: 
 Connect to the server 
 Writes data 
 Waits for and receives the exact same data back from the server. 
 Closes the connection 

#### Bootstrapping the server 

#### Implementing the client logic 
ChannelHandlerAdapter

- channelActive()Called after the connection to the server is established 
- channelRead0()Called after you receive data from the server 
- exceptionCaught()Called if any exception was raised during processing 