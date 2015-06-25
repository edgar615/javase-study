package eventbus;

import com.google.common.eventbus.EventBus;

/**
 * Created by edgar on 15-6-25.
 */
public class DeadEventExample {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        DeadEventSubscriber subscriber = new DeadEventSubscriber(eventBus);
        SimpleTradeExecutor executor = new SimpleTradeExecutor(eventBus);
        executor.executeTrade(100, "buy");

    }
}
