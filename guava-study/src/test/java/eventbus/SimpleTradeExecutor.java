package eventbus;

import com.google.common.eventbus.EventBus;

import java.util.Date;

/**
 * Created by edgar on 15-6-25.
 */
public class SimpleTradeExecutor {
    private EventBus eventBus;

    public SimpleTradeExecutor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void executeTrade(double amount, String type) {
        TradeAccountEvent event = processTrade(amount, type);
        eventBus.post(event);
    }


    private TradeAccountEvent processTrade(double amount, String type) {
        Date executiontime = new Date();
        String message = String.format("Proccessed trade amount %s @ %s", amount, executiontime);
        TradeAccountEvent event = new TradeAccountEvent(amount, executiontime, type);
        System.out.println(message);
        return event;
    }
}
