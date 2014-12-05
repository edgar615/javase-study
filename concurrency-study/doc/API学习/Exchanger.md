Exchanger允许在2个线程间定义同步点，当2个线程到达这个点，他们相互交换数据类型，使用第一个线程的数据类型变成第二个的，然后第二个线程的数据类型变成第一个的。

- exchange(V x) 等待另一个线程到达此交换点（除非当前线程被中断），然后将给定的对象传送给该线程，并接收该线程的对象
- exchange(V x, long timeout, TimeUnit unit) 等待另一个线程到达此交换点（除非当前线程被中断，或者超出了指定的等待时间），然后将给定的对象传送给该线程，同时接收该线程的对象。

<pre>
public class ExchangerRunnable implements Runnable {

    private Exchanger<Object> exchanger;
    Object    object    = null;

    public ExchangerRunnable(Exchanger<Object> exchanger, Object object) {
        this.exchanger = exchanger;
        this.object = object;
    }

    @Override
    public void run() {
        Object previous = this.object;
        try {
            this.object = this.exchanger.exchange(this.object);
            System.out.println(
                    Thread.currentThread().getName() +
                            " exchanged " + previous + " for " + this.object);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
</pre>

<pre>
public static void main(String[] args) {
    Exchanger exchanger = new Exchanger();

    ExchangerRunnable exchangerRunnable1 =
            new ExchangerRunnable(exchanger, "A");

    ExchangerRunnable exchangerRunnable2 =
            new ExchangerRunnable(exchanger, "B");

    new Thread(exchangerRunnable1).start();
    new Thread(exchangerRunnable2).start();
}
</pre>