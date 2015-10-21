package com.edgar.kafka.string;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

public class StringConsumerRunnable implements Runnable {
    private KafkaStream<String, String> stream;

    private int threadNumber;

    public StringConsumerRunnable(KafkaStream<String, String> stream, int threadNumber) {
        this.stream = stream;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        ConsumerIterator<String, String> iterator = stream.iterator();
        while (iterator.hasNext()) {
            MessageAndMetadata<String, String> metadata = iterator.next();
            String key = metadata.key();
            String msg = metadata.message();
            long offset = metadata.offset();
            int partition = metadata.partition();
            System.out.println("Thread " + threadNumber + ": " + key + " - " + msg + " partition" + partition + " offset" + offset);
        }
        System.out.println("Shutting down Thread: " + threadNumber);
    }
}