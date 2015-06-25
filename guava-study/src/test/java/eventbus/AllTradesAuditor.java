package eventbus;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.List;

/**
 * Created by edgar on 15-6-25.
 */
public class AllTradesAuditor {
    private List<BuyEvent> buyEvents = Lists.newArrayList();
    private List<SellEvent> sellEvents = Lists.newArrayList();

    public AllTradesAuditor(EventBus eventBus) {
        eventBus.register(this);
    }


    @Subscribe
    public void auditBuy(BuyEvent buyEvent) {
        buyEvents.add(buyEvent);
        System.out.println("Received trade " + buyEvent);
        System.out.println("buyEvents " + buyEvents.size());
    }

    @Subscribe
    public void auditSell(SellEvent sellEvent) {
        sellEvents.add(sellEvent);
        System.out.println("Received trade " + sellEvent);
        System.out.println("sellEvents " + sellEvents.size());
    }
}
