package eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by edgar on 15-6-25.
 */
public class AsyncEventBusExample {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        EventBus eventBus = new AsyncEventBus(service);
        SimpleTradeAuditor auditor = new SimpleTradeAuditor(eventBus);
        SimpleTradeExecutor executor = new SimpleTradeExecutor(eventBus);
        executor.executeTrade(100, "buy");
        
        executor.executeTrade(200, "sell");
        service.shutdown();
    }
}
