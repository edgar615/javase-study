package eventbus;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.List;

/**
 * Created by edgar on 15-6-25.
 */
public class SimpleTradeAuditor {
    private List<TradeAccountEvent> tradeAccountEvents = Lists.newArrayList();

    public SimpleTradeAuditor(EventBus eventBus) {
        eventBus.register(this);
    }

    public void unregister(EventBus eventBus) {
        eventBus.unregister(this);
    }

    @Subscribe
    public void auditTrade(TradeAccountEvent tradeAccountEvent) {
        tradeAccountEvents.add(tradeAccountEvent);
        System.out.println("Received trade " + tradeAccountEvent);
        System.out.println("tradeAccountEvents " + tradeAccountEvents.size());
    }
}
