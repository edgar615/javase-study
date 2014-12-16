package concurrencyinpractice.chapter12;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Administrator on 2014/12/16.
 */
public class BoundedBufferTest {

    @Test
    public void testIsEmptyWhenConstructed() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        Assert.assertTrue(bb.isEmpty());
        Assert.assertFalse(bb.isFull());
    }

    @Test
    public void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        for (int i = 0; i < 10; i ++) {
            bb.put(i);
        }
        Assert.assertFalse(bb.isEmpty());
        Assert.assertTrue(bb.isFull());
    }

    @Test
    public void testTakeBlocksWhenEmpty() {
        final BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        Thread taker = new Thread() {
            @Override
            public void run() {
                try {
                    int unused = bb.take();
                    Assert.fail();
                } catch (InterruptedException e) {
                }
            }
        };
        try {
            taker.start();
            Thread.sleep(1000);
            taker.interrupt();
            taker.join(1000);
            Assert.assertFalse(taker.isAlive());
        } catch (InterruptedException e) {
            Assert.fail();
        }
    }

    @Test
    public void testPutBlocksWhenFull() throws InterruptedException {
        final BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        for (int i = 0; i < 10; i ++) {
            bb.put(i);
        }
        Thread puter = new Thread() {
            @Override
            public void run() {
                try {
                    bb.put(1);
                    Assert.fail();
                } catch (InterruptedException e) {

                }
            }
        };

        try {
            puter.start();
            Thread.sleep(1000);
            puter.interrupt();
            puter.join(1000);
            Assert.assertFalse(puter.isAlive());
        } catch (InterruptedException e) {
            Assert.fail();
        }

    }

    class Big {
        double[] data = new double[100000];
    }

    void testLeak() throws InterruptedException {
        BoundedBuffer<Big> bb = new BoundedBuffer<Big>(10000);
        int heapSize1 = snapshotHeap();
        for (int i = 0; i < 10000; i++)
            bb.put(new Big());
        for (int i = 0; i < 10000; i++)
            bb.take();
        int heapSize2 = snapshotHeap();
        Assert.assertTrue(Math.abs(heapSize1 - heapSize2) < 10000);
    }

    private int snapshotHeap() {
        /* Snapshot heap and return heap size */
        return 0;
    }

}
