package eventbus;

import java.util.Date;

/**
 * Created by edgar on 15-6-25.
 */
public class SellEvent extends TradeAccountEvent {
    public SellEvent(double amount, Date tradeExecutionTime) {
        super(amount, tradeExecutionTime, "sell");
    }
}
