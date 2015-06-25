package eventbus;

import com.google.common.eventbus.EventBus;

import java.util.Date;

/**
 * Created by edgar on 15-6-25.
 */
public class AllTradeExecutor {
    private EventBus eventBus;

    public AllTradeExecutor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void executeTrade(double amount, String type) {
        TradeAccountEvent event = processTrade(amount, type);
        eventBus.post(event);
    }


    private TradeAccountEvent processTrade(double amount, String type) {
        Date executiontime = new Date();
        String message = String.format("Proccessed trade amount %s @ %s", amount, executiontime);
        System.out.println(message);
        if ("sell".equals(type)) {
            return new SellEvent(amount, executiontime);
        } else if ("buy".equals(type)){
            return new BuyEvent(amount, executiontime);
        }
        return new TradeAccountEvent(amount, executiontime, type);
    }
}
