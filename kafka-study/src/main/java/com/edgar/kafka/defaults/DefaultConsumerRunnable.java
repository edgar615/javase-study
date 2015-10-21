package com.edgar.kafka.defaults;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

public class DefaultConsumerRunnable implements Runnable {
    private KafkaStream<byte[], byte[]> stream;

    private int threadNumber;

    public DefaultConsumerRunnable(KafkaStream<byte[], byte[]> stream, int threadNumber) {
        this.stream = stream;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
        while (iterator.hasNext()) {
            MessageAndMetadata<byte[], byte[]> metadata = iterator.next();
            String key = new String(metadata.key());
            String msg = new String(metadata.message());
            long offset = metadata.offset();
            int partition = metadata.partition();
            System.out.println("Thread " + threadNumber + ": " + key + " - " + msg + " partition" + partition + " offset" + offset);
        }
        System.out.println("Shutting down Thread: " + threadNumber);
    }
}