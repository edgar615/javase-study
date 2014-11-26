package concurreny.exchanger;

import java.util.concurrent.Exchanger;

/**
 * Created by Administrator on 2014/11/26.
 */
public class Main {

    public static void main(String[] args) {
        Exchanger exchanger = new Exchanger();

        ExchangerRunnable exchangerRunnable1 =
                new ExchangerRunnable(exchanger, "A");

        ExchangerRunnable exchangerRunnable2 =
                new ExchangerRunnable(exchanger, "B");

        new Thread(exchangerRunnable1).start();
        new Thread(exchangerRunnable2).start();
    }
}
