package channel;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelPipeline;

public class ModifyChannelPipeline {

    public static void modifyPipeline() {
        ChannelPipeline ch = null;
        FirstHandler firstHandler = new FirstHandler();
        ch.addLast("handler1", firstHandler);
        ch.addFirst("handler2", new SecondHandler());
        ch.addLast("handler3", new ThirdHandler());

        ch.remove("handler3");
        ch.remove(firstHandler);
        ch.replace("handler2", "handler4", new ForthHandler());
    }

    private static final class FirstHandler extends ChannelHandlerAdapter {

    }

    private static final class SecondHandler extends ChannelHandlerAdapter {

    }

    private static final class ThirdHandler extends ChannelHandlerAdapter {

    }

    private static final class ForthHandler extends ChannelHandlerAdapter {

    }
}