package com.edgar.kafka.alarm;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by edgar on 14-10-22.
 */
public class AlarmConsumerExample {

    public static void main(String[] args) throws IOException {

        Properties properties = getPropertiesFromLocalFile("src/main/java/com/edgar/kafka/consumer.properties");
        ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(createConfig("csst", properties));
//        KafkaStream<byte[], byte[]> kafkaStream =
        Map<String, Integer> topicMap = new HashMap<String, Integer>();
        topicMap.put("my_test3", 3);
        ExecutorService exec = Executors.newFixedThreadPool(3);
        Map<String, List<KafkaStream<String, Alarm>>> map = consumerConnector.createMessageStreams(topicMap, new StringDecoder(null), new AlarmDecoder(null));
        List<KafkaStream<String, Alarm>> streams = map.get("my_test3");
        int threadNumber = 0;
        for (KafkaStream<String, Alarm> stream : streams) {
            exec.execute(new AlarmConsumerRunnable(stream, threadNumber));
            threadNumber ++;
        }

    }

    private static ConsumerConfig createConfig(String groupId, Properties properties) {
        properties.put("group.id", groupId);
        ConsumerConfig consumerConfig = new ConsumerConfig(properties);
        return consumerConfig;
    }

    private static Properties getPropertiesFromLocalFile(String location) throws IOException {
        Properties properties = new Properties();
        File file = new File(location);
        InputStream is = new FileInputStream(file);
        properties.load(is);
        return properties;
    }
}
