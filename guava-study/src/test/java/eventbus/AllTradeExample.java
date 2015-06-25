package eventbus;

import com.google.common.eventbus.EventBus;

/**
 * Created by edgar on 15-6-25.
 */
public class AllTradeExample {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        AllTradesAuditor auditor = new AllTradesAuditor(eventBus);
        AllTradeExecutor executor = new AllTradeExecutor(eventBus);
        executor.executeTrade(100, "buy");

        executor.executeTrade(200, "sell");
    }
}
