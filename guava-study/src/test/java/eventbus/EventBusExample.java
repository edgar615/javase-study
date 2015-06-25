package eventbus;

import com.google.common.eventbus.EventBus;

/**
 * Created by edgar on 15-6-25.
 */
public class EventBusExample {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        SimpleTradeAuditor auditor = new SimpleTradeAuditor(eventBus);
        SimpleTradeExecutor executor = new SimpleTradeExecutor(eventBus);
        executor.executeTrade(100, "buy");

        auditor.unregister(eventBus);
        executor.executeTrade(200, "sell");
    }
}
