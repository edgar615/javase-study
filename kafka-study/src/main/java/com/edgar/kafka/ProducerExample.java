package com.edgar.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Created by Edgar on 2015/12/11.
 *
 * @author Edgar  Date 2015/12/11
 */
public class ProducerExample {
  public static void main(String[] args) {

    Properties props = new Properties();
    props.put("bootstrap.servers", "192.168.149.136:9092");
    props.put("acks", "all");
    props.put("retries", 0);
    props.put("batch.size", 16384);
    props.put("linger.ms", 1);
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    Producer<String, String> producer = new KafkaProducer(props);
    for(int i = 0; i < 100; i++) {
      System.out.println(i);
      producer.send(new ProducerRecord<String, String>("my-topic4", Integer.toString(i), Integer
              .toString(i)));
    }

    producer.close();
  }
}
