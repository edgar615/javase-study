package com.edgar.kafka.alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

import java.io.IOException;

/**
 * Created by edgar on 14-10-22.
 */
public class AlarmConsumerRunnable implements Runnable {

    private KafkaStream<String, Alarm> stream;

    private int threadNumber;

    public AlarmConsumerRunnable(KafkaStream<String, Alarm> stream, int threadNumber) {
        this.stream = stream;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        ConsumerIterator<String, Alarm> iterator = stream.iterator();
        while (iterator.hasNext()) {
            MessageAndMetadata<String, Alarm> metadata = iterator.next();
            String key = metadata.key();
            Alarm alarm = metadata.message();
            long offset = metadata.offset();
            int partition = metadata.partition();
            System.out.println("Thread " + threadNumber + ": " + key + " - " + alarm + " partition" + partition + " offset" + offset);
        }
        System.out.println("Shutting down Thread: " + threadNumber);
    }
}
