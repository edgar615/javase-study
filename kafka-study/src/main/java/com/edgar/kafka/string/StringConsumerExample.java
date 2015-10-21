package com.edgar.kafka.string;

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
public class StringConsumerExample {

    public static void main(String[] args) throws IOException {
        Properties properties = getPropertiesFromLocalFile("src/main/java/com/edgar/kafka/consumer.properties");
        ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(createConfig("csst", properties));
        int numThreads = 3;
        String topic = "my_test2";
        List<KafkaStream<String, String>> streams = getStreams(topic, numThreads, consumerConnector);

        ExecutorService exec = Executors.newFixedThreadPool(numThreads);
        int threadNumber = 0;
        for (KafkaStream<String, String> stream : streams) {
            exec.execute(new StringConsumerRunnable(stream, threadNumber));
            threadNumber ++;
        }
    }

    private static List<KafkaStream<String, String>> getStreams(String topic, int numThreads, ConsumerConnector consumerConnector) {
        Map<String,Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, numThreads);
        Map<String, List<KafkaStream<String, String>>> map = consumerConnector.createMessageStreams(topicCountMap, new StringDecoder(null), new StringDecoder(null));
        return map.get(topic);
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
