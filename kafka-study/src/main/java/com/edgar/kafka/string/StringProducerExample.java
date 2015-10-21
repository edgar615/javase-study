package com.edgar.kafka.string;

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
public class StringProducerExample {

    public static void main(String[] args) throws IOException {
        Properties properties = getPropertiesFromLocalFile("src/main/java/com/edgar/kafka/producer.properties");
        ProducerConfig config = new ProducerConfig(properties);
        Producer<String, String> producer = new Producer<String, String>(config);
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

    private static KeyedMessage<String, String> createMessage() {
        Random rnd = new Random();
        long runtime = new Date().getTime();
        String ip = "10.4.7." + rnd.nextInt(255);
        String msg = runtime + ",www.csst.com," + ip;
        return new KeyedMessage<String, String>("my_test", ip, msg);
    }
}
