package com.edgar.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/10/11.
 */
public class TestProducer {

    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
// "metadata.broker.list" defines where the Producer can find a one or more Brokers to determine the Leader for each topic.
// This does not need to be the full set of Brokers in your cluster but should include at least two in case the first Broker is not available.
// No need to worry about figuring out which Broker is the leader for the topic (and partition),
// the Producer knows how to connect to the Broker and ask for the meta data then connect to the correct Broker.
        props.put("metadata.broker.list", "localhost:9093,localhost:9094,localhost:9095");
//        The second property "serializer.class" defines what Serializer to use when preparing the message for transmission to the Broker.
//        In our example we use a simple String encoder provided as part of Kafka.
//        Note that the encoder must accept the same type as defined in the KeyedMessage object in the next step.
        props.put("serializer.class", "kafka.serializer.StringEncoder");
//        "partitioner.class" defines what class to use to determine which Partition in the Topic the message is to be sent to.
//          This is optional, but for any non-trivial implementation you are going to want to implement a partitioning scheme.
//          More about the implementation of this class later.
//          If you include a value for the key but haven't defined a partitioner.class Kafka will use the default partitioner.
//      If the key is null, then the Producer will assign the message to a random Partition.
        props.put("partitioner.class", "com.edgar.kafka.SimplePartitioner");
//        "request.required.acks" tells Kafka that you want your Producer to require an acknowledgement from the Broker that the message was received.
//          Without this setting the Producer will 'fire and forget' possibly leading to data loss.
//        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);

        long nEvents = 0;
        while(true) {
            Random rnd = new Random();
            long runtime = new Date().getTime();
            String ip = "10.4.7." + rnd.nextInt(255);
            String msg = runtime + ",www.csst.com," + ip;
//        The “page_visits” is the Topic to write to. Here we are passing the IP as the partition key.
//        Note that if you do not include a key, even if you've defined a partitioner class, Kafka will assign the message to a random partition.
            KeyedMessage<String, String> data = new KeyedMessage<String, String>("my_test", ip, msg);
            producer.send(data);
            nEvents ++;
            TimeUnit.SECONDS.sleep(1);
        }
//        producer.close();
    }
}
