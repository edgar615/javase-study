package com.edgar.kafka.simple;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.cluster.Broker;
import kafka.common.ErrorMapping;
import kafka.common.TopicAndPartition;
import kafka.javaapi.*;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.message.MessageAndOffset;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * Created by edgar on 14-10-23.
 */
public class SimpleConsumerExample {

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 9093;
        String topic = "my_test";
        int partition = 0;

        run(topic, partition, host, port);
//        System.out.println(offset);

    }

    private static void run(String topic, int partition, String host, int port) throws Exception {
        PartitionMetadata leaderMetada = findLeader(Arrays.asList(host), port, topic, partition);
        if (leaderMetada == null) {
            System.out.println("Can't find metadata for Topic and Partition. Exiting");
            return;
        }
        if (leaderMetada.leader() == null) {
            System.out.println("Can't find Leader for Topic and Partition. Exiting");
            return;
        }
        List<String> replicaBrokers = new ArrayList<String>();
        replicaBrokers.clear();
        for (Broker replica : leaderMetada.replicas()) {
            replicaBrokers.add(replica.host());
        }
        String clientName = "Client_" + topic + "_" + partition;

        String leaderBroker = leaderMetada.leader().host();
        port = leaderMetada.leader().port();
        SimpleConsumer consumer = new SimpleConsumer(leaderBroker, port, 10000, 64 * 1024, clientName);
        long offset = getLastOffset(consumer, topic, partition, kafka.api.OffsetRequest.EarliestTime(), clientName);
        int numErrors = 0;
        while (true) {
            if (consumer == null) {
                consumer = new SimpleConsumer(leaderBroker, port, 10000, 64 * 1024, clientName);
            }
            FetchRequest request = new FetchRequestBuilder().clientId(clientName).addFetch(topic, partition, offset, 100000).build();
            FetchResponse fetchResponse = consumer.fetch(request);
            if (fetchResponse.hasError()) {
                numErrors++;
                // Something went wrong!
                short code = fetchResponse.errorCode(topic, partition);
                System.out.println("Error fetching data from the Broker:" + leaderBroker + " Reason: " + code);
                if (numErrors > 5) break;
                if (code == ErrorMapping.OffsetOutOfRangeCode())  {
                    // We asked for an invalid offset. For simple case ask for the last element to reset
                    offset = getLastOffset(consumer,topic, partition, kafka.api.OffsetRequest.LatestTime(), clientName);
                    continue;
                }
                consumer.close();
                consumer = null;
                leaderBroker = findNewLeader(leaderBroker, topic, partition, port, replicaBrokers);
                continue;
            }

            for (MessageAndOffset messageAndOffset : fetchResponse.messageSet(topic, partition)) {
                long currentOffset = messageAndOffset.offset();
                if (currentOffset < offset) {
                    System.out.println("Found an old offset: " + currentOffset + " Expecting: " + offset);
                    continue;
                }
                offset = messageAndOffset.nextOffset();
                ByteBuffer payload = messageAndOffset.message().payload();

                byte[] bytes = new byte[payload.limit()];
                payload.get(bytes);
                System.out.println(String.valueOf(messageAndOffset.offset()) + ": " + new String(bytes));
            }
        }
    }

    private static String findNewLeader(String oldLeader, String topic, int partition, int port, List<String> replicaBrokers) throws Exception {
        for (int i = 0; i < 3; i ++) {
            boolean goToSleep = false;
            PartitionMetadata metadata = findLeader(replicaBrokers, port, topic, partition);
            if (metadata == null) {
                goToSleep = true;
            } else if (metadata.leader() == null) {
                goToSleep = true;
            } else if (oldLeader.equalsIgnoreCase(metadata.leader().host()) && i == 0) {
                // first time through if the leader hasn't changed give ZooKeeper a second to recover
                // second time, assume the broker did recover before failover, or it was a non-Broker issue
                //
                goToSleep = true;
            } else {
                return metadata.leader().host();
            }
            if (goToSleep) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
        }
        System.out.println("Unable to find new leader after Broker failure. Exiting");
        throw new Exception("Unable to find new leader after Broker failure. Exiting");
    }


    private static long getLastOffset(SimpleConsumer consumer, String topic, int partition, long whichTime, String clientName) {
        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> map = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
        map.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, 1));
        OffsetRequest request = new OffsetRequest(map, kafka.api.OffsetRequest.CurrentVersion(), clientName);
        OffsetResponse offsetResponse = consumer.getOffsetsBefore(request);
        if (offsetResponse.hasError()) {
            System.out.println("Error fetching data Offset Data the Broker. Reason: " + offsetResponse.errorCode(topic, partition));
            return 0;
        }
        long[] offsets = offsetResponse.offsets(topic, partition);
        return offsets[0];
    }

    private static List<TopicMetadata> getTopicMetadata(String host, int port, String topic) {
        SimpleConsumer consumer = null;
        try {
            consumer = new SimpleConsumer(host, port, 10000, 64 * 1024, "leaderLookup");
            TopicMetadataRequest request = new TopicMetadataRequest(Arrays.asList(topic));
            TopicMetadataResponse response = consumer.send(request);
            List<TopicMetadata> metadatas = response.topicsMetadata();
            return response.topicsMetadata();
        } finally {
            if (consumer != null) {
                consumer.close();
            }
        }
    }

    private static PartitionMetadata findLeader(List<String> brokers, int port, String topic, int partition) {
        PartitionMetadata leaderMetada = null;
        for (String broker : brokers) {
            List<TopicMetadata> metadatas = getTopicMetadata(broker, port, topic);
            for (TopicMetadata metadata : metadatas) {
                if (leaderMetada == null) {
                    for (PartitionMetadata partitionMetadata : metadata.partitionsMetadata()) {
                        if (partition == partitionMetadata.partitionId()) {
                            leaderMetada = partitionMetadata;
                        }
                    }
                }
            }
        }
        return leaderMetada;
    }
}
