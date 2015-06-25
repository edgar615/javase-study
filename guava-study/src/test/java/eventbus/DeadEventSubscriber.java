package eventbus;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.logging.Logger;

/**
 * Created by edgar on 15-6-25.
 */
public class DeadEventSubscriber {
    private static final Logger logger = Logger.getLogger(DeadEventSubscriber.class.getName());

    public DeadEventSubscriber(EventBus eventBus) {
        eventBus.register(this);
    }

    @Subscribe
    public void handleUnsubscribedEvent(DeadEvent deadEvent) {
        System.out.println("NO subscribers for " + deadEvent.getEvent());
    }
}
