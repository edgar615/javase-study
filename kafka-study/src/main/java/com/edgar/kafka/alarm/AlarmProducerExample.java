package com.edgar.kafka.alarm;

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
public class AlarmProducerExample {
    public static void main(String[] args) throws IOException {
        Properties properties = getPropertiesFromLocalFile("src/main/java/com/edgar/kafka/alarm-producer.properties");
        ProducerConfig config = new ProducerConfig(properties);
        Producer<String, Alarm> producer = new Producer<String, Alarm>(config);
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

    private static KeyedMessage<String, Alarm> createMessage() {
        Random rnd = new Random();
        long runtime = new Date().getTime();
        String ip = "10.4.7." + rnd.nextInt(255);
        String msg = runtime + ",www.csst.com," + ip;
        Alarm alarm = new Alarm(rnd.nextInt(255), ip, msg);
        return new KeyedMessage<String, Alarm>("my_test3", ip, alarm);
    }
}
