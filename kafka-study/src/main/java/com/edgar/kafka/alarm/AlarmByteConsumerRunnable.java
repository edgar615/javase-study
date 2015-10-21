package com.edgar.kafka.alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

import java.io.IOException;

/**
 * Created by edgar on 14-10-22.
 */
public class AlarmByteConsumerRunnable implements Runnable {

    private KafkaStream<byte[], byte[]> stream;

    private int threadNumber;

    public AlarmByteConsumerRunnable(KafkaStream<byte[], byte[]> stream, int threadNumber) {
        this.stream = stream;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
        while (iterator.hasNext()) {
            MessageAndMetadata<byte[], byte[]> metadata = iterator.next();
            String key = new String(metadata.key());
            long offset = metadata.offset();
            int partition = metadata.partition();
            ObjectMapper mapper = new ObjectMapper();
            try {
                Alarm alarm = mapper.readValue(metadata.message(), Alarm.class);
                System.out.println("Thread " + threadNumber + ": " + key + " - " + alarm + " partition" + partition + " offset" + offset);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Shutting down Thread: " + threadNumber);
    }
}
