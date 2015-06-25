package eventbus;

import java.util.Date;

/**
 * Created by edgar on 15-6-25.
 */
public class TradeAccountEvent {

    private double amount;
    private Date tradeExecutionTime;

    private String type;

    public TradeAccountEvent(double amount, Date tradeExecutionTime, String type) {
        this.amount = amount;
        this.tradeExecutionTime = tradeExecutionTime;
        this.type = type;
    }
}
