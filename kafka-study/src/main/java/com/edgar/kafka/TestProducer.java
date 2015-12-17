package com.edgar.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class TestProducer {

  public static void main(String[] args) {
    Random rnd = new Random();

    Properties props = new Properties();
    props.put("bootstrap.servers", "10.4.7.48:9092 ");
    props.put("serializer.class", "kafka.serializer.StringEncoder");
    props.put("request.required.acks", "1");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");


    Producer<String, String> producer = new KafkaProducer<String, String>(props);

    for (long nEvents = 0; nEvents < 100; nEvents++) {
      long runtime = new Date().getTime();
      String ip = "192.168 .2." + rnd.nextInt(255);
      String msg = runtime + ", www.example.com, "+ip;

      ProducerRecord<String, String> data = new ProducerRecord<String, String>("my-topic2", ip, msg);
      producer.send(data);
    }
    producer.close();
  }
}