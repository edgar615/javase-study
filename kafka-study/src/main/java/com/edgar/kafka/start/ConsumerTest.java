package com.edgar.kafka.start;

import kafka.Kafka;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

import java.util.Properties;

/**
 * Created by Administrator on 2014/10/20.
 */
public class ConsumerTest implements Runnable {

    private KafkaStream kafkaStream;

    private int threadNumber;

    public ConsumerTest(KafkaStream kafkaStream, int threadNumber) {
        this.kafkaStream = kafkaStream;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        try {
            ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();
            while (iterator.hasNext()) {
                MessageAndMetadata<byte[], byte[]> metadata = iterator.next();
                System.out.println("Thread " + threadNumber + ": " + new String(metadata.key()) + " - "+ new String(metadata.message()) + " partition" + metadata.partition() + " offset" + metadata.offset());
            }
            System.out.println("Shutting down Thread: " + threadNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
