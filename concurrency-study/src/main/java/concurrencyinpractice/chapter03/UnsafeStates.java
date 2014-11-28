package concurrencyinpractice.chapter03;

/**
 * Created by Administrator on 2014/11/28.
 */
public class UnsafeStates {

    private String[] states = new String[] {
            "AK", "AL"
    };

    public String[] getStates() {
        return states;
    }
}
