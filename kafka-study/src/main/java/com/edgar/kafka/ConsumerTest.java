package com.edgar.kafka;

import kafka.Kafka;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

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
            ConsumerIterator<String, byte[]> iterator = kafkaStream.iterator();
            while (iterator.hasNext()) {
                System.out.println("Thread " + threadNumber + ": " + new String(iterator.next().message()));
            }
            System.out.println("Shutting down Thread: " + threadNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
