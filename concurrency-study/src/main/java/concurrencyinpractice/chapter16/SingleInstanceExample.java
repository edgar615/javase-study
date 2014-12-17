package concurrencyinpractice.chapter16;

/**
 * Created by Administrator on 2014/12/17.
 */
public class SingleInstanceExample {

    public static void main(String[] args) {
        SingleInstance singleInstance = new SingleInstance();
        SingleInstance.getResource();
    }
}
