package eventloop;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.TimeUnit;

/**
 * Created by Edgar on 2016/4/20.
 *
 * @author Edgar  Date 2016/4/20
 */
public class EventloopExample {
  public static void main(String[] args) {
    EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
    System.out.println("main thread:" + Thread.currentThread().getId());
    Future future = eventLoopGroup.submit(new Runnable() {
      @Override
      public void run() {
        try {
          TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("hoho");
        System.out.println("run thread:" + Thread.currentThread().getId());
      }
    });
    future.addListener(new GenericFutureListener<Future<?>>() {
      @Override
      public void operationComplete(Future future) throws Exception {
        System.out.println("complete thread:" + Thread.currentThread().getId());
          System.out.println(future.isSuccess());
      }
    });
  }
}
