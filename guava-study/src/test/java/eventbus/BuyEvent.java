package eventbus;

import java.util.Date;

/**
 * Created by edgar on 15-6-25.
 */
public class BuyEvent extends TradeAccountEvent {
    public BuyEvent(double amount, Date tradeExecutionTime) {
        super(amount, tradeExecutionTime, "buy");
    }
}
