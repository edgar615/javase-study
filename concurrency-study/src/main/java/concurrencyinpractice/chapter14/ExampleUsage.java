package concurrencyinpractice.chapter14;

public class ExampleUsage {
    private GrumpyBoundedBuffer<String> buffer;
    int SLEEP_GRANULARITY = 50;

    public static void main(String[] args) throws InterruptedException {
        ExampleUsage usage = new ExampleUsage();
        usage.buffer = new GrumpyBoundedBuffer<>(10);
        usage.useBuffer();
    }

    void useBuffer() throws InterruptedException {
        while (true) {
            try {
                String item = buffer.take();
                // use item
                break;
            } catch (BufferEmptyException e) {
                System.out.println("buffer is empty");
                Thread.sleep(SLEEP_GRANULARITY);
            }
        }
    }
}