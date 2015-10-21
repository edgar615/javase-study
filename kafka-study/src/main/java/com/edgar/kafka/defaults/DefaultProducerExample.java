package com.edgar.kafka.defaults;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * Created by edgar on 14-10-22.
 */
public class DefaultProducerExample {

    public static void main(String[] args) throws IOException {
        Properties properties = getPropertiesFromLocalFile("src/main/java/com/edgar/kafka/default-producer.properties");
        ProducerConfig config = new ProducerConfig(properties);
        Producer<byte[], byte[]> producer = new Producer<byte[], byte[]>(config);
        long msgSize = 10;
        for (int i = 0; i < msgSize; i ++) {
            producer.send(createMessage());
        }
    }

    private static Properties getPropertiesFromLocalFile(String location) throws IOException {
        Properties properties = new Properties();
        File file = new File(location);
        InputStream is = new FileInputStream(file);
        properties.load(is);
        return properties;
    }

    private static KeyedMessage<byte[], byte[]> createMessage() {
        Random rnd = new Random();
        long runtime = new Date().getTime();
        String ip = "10.4.7." + rnd.nextInt(255);
        String msg = runtime + ",www.csst.com," + ip;
        return new KeyedMessage<byte[], byte[]>("my_alarm2", ip.getBytes(), msg.getBytes());
    }
}
