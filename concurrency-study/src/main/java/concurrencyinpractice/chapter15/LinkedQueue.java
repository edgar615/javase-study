package concurrencyinpractice.chapter15;

import concurrencyinpractice.annotation.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class LinkedQueue <E> {

    private static class Node <E> {
        final E item;
        final AtomicReference<Node<E>> next;

        public Node(E item, LinkedQueue.Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<LinkedQueue.Node<E>>(next);
        }
    }

    private final LinkedQueue.Node<E> dummy = new LinkedQueue.Node<E>(null, null);
    private final AtomicReference<LinkedQueue.Node<E>> head
            = new AtomicReference<LinkedQueue.Node<E>>(dummy);
    private final AtomicReference<LinkedQueue.Node<E>> tail
            = new AtomicReference<LinkedQueue.Node<E>>(dummy);

    public boolean put(E item) {
        LinkedQueue.Node<E> newNode = new LinkedQueue.Node<E>(item, null);
        while (true) {
            //尾节点
            LinkedQueue.Node<E> curTail = tail.get();
            LinkedQueue.Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) { //检查队列是否处于中间状态（有无另一个线程正在插入元素）
                if (tailNext != null) {
                    // tailNext不为null，表示队列已经被修改；队列处于中间状态，将尾节点推进到tailNext
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    // 队列处于稳定状态，增加新节点
                    if (curTail.next.compareAndSet(null, newNode)) {
                        // 将尾节点推进到newNode
                        tail.compareAndSet(curTail, newNode);
                        return true;
                    }
                }
            }
        }
    }
}