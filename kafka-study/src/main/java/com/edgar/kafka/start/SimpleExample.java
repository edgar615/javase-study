package com.edgar.kafka.start;

import kafka.api.*;
import kafka.api.FetchRequest;
import kafka.cluster.Broker;
import kafka.common.ErrorMapping;
import kafka.common.TopicAndPartition;
import kafka.javaapi.FetchResponse;
import kafka.javaapi.OffsetRequest;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.TopicMetadataResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.message.MessageAndOffset;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by edgar on 14-10-22.
 */
public class SimpleExample {

    public static void main(String[] args) {
        SimpleExample example = new SimpleExample();
        long maxReads = 1;
        String topic = "my_test2";
        int partition = 2;
        List<String> seeds = new ArrayList<String>();
        seeds.add("localhost");
        int port = 9093;
        try {
            example.run(maxReads, topic, partition, seeds, port);
        } catch (Exception e) {
            System.out.println("Oops:" + e);
            e.printStackTrace();
        }
    }

    private List<String> m_replicaBrokers = new ArrayList<String>();

//    Finding the Lead Broker for a T9093opic and Partition

    private PartitionMetadata findLeader(List<String> seedBrokers, int port, String topic, int partitionId) {
        PartitionMetadata runMetada = null;
        for (String seedBroker : seedBrokers) {
            SimpleConsumer consumer = null;
            try {
                consumer = new SimpleConsumer(seedBroker, port, 100000, 64 * 1024, "leaderLookup");
                List<String> topics = Collections.singletonList(topic);
                TopicMetadataRequest req = new TopicMetadataRequest(topics);
                TopicMetadataResponse resp = consumer.send(req);
                List<TopicMetadata> topicMetadatas = resp.topicsMetadata();
                for (TopicMetadata topicMetadata : topicMetadatas) {
                    for (PartitionMetadata part : topicMetadata.partitionsMetadata()) {
                        if (part.partitionId() == partitionId) {
                            runMetada = part;
                            break;
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("Error communicating with Broker [" + seedBroker + "] to find Leader for [" + topic
                        + ", " + partitionId + "] Reason: " + e);
            } finally {
                if (consumer != null) consumer.close();
            }
        }
        if (runMetada != null) {
            m_replicaBrokers.clear();
            for (Broker replica : runMetada.replicas()) {
                m_replicaBrokers.add(replica.host());
            }
        }
        return runMetada;
    }

    private static long getLastOffset(SimpleConsumer consumer, String topic, int partitionId, long whichTime, String clientName) {
        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partitionId);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfoMap = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
        requestInfoMap.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, 1));
        OffsetRequest offsetRequest = new OffsetRequest(requestInfoMap, kafka.api.OffsetRequest.CurrentVersion(), clientName);
        OffsetResponse response = consumer.getOffsetsBefore(offsetRequest);
        if (response.hasError()) {
            System.out.println("Error fetching data Offset Data the Broker. Reason: " + response.errorCode(topic, partitionId));
            return 0;
        }
        long[] offsets = response.offsets(topic, partitionId);
        return offsets[0];
    }

    private String findNewLeader(String oldLeader, int port, String topic, int partitionId) throws Exception {
        for (int i = 0; i < 3; i++) {
            boolean goToSleep = false;
            PartitionMetadata metadata = findLeader(m_replicaBrokers, port, topic, partitionId);
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
                    TimeUnit.SECONDS.sleep(1000);
                } catch (InterruptedException e) {

                }
            }
        }

        System.out.println("Unable to find new leader after Broker failure. Exiting");
        throw new Exception("Unable to find new leader after Broker failure. Exiting");
    }

    public void run(long maxReads, String topic, int partitionId, List<String> seedBrokers, int port) throws Exception {
        PartitionMetadata metadata = findLeader(seedBrokers, port, topic, partitionId);
        if (metadata == null) {
            System.out.println("Can't find metadata for Topic and Partition. Exiting");
            return;
        }
        if (metadata.leader() == null) {
            System.out.println("Can't find Leader for Topic and Partition. Exiting");
            return;
        }
        String leadBroker = metadata.leader().host();
        String clientName = "Client_" + topic + "_" + partitionId;
        SimpleConsumer consumer = new SimpleConsumer(leadBroker, port, 10000, 64 * 1024, clientName);
        long readOffset = getLastOffset(consumer, topic, partitionId, kafka.api.OffsetRequest.EarliestTime(), clientName);

        int numErrors = 0;
        while (maxReads > 0) {
            if (consumer == null) {
                consumer = new SimpleConsumer(leadBroker, port, 100000, 64 * 1024, clientName);
            }
            FetchRequest req = new FetchRequestBuilder()
                    .clientId(clientName)
                    .addFetch(topic, partitionId, readOffset, 100000) // Note: this fetchSize of 100000 might need to be increased if large batches are written to Kafka
                    .build();
            FetchResponse response = consumer.fetch(req);
            if (response.hasError()) {
                numErrors++;
                short code = response.errorCode(topic, partitionId);
                System.out.println("Error fetching data from the Broker:" + leadBroker + " Reason: " + code);
                if (numErrors > 5) {
                    break;
                }
                if (code == ErrorMapping.OffsetOutOfRangeCode()) {
                    // We asked for an invalid offset. For simple case ask for the last element to reset
                    readOffset = getLastOffset(consumer, topic, partitionId, kafka.api.OffsetRequest.LatestTime(), clientName);
                    continue;
                }
                consumer.close();
                consumer = null;
                leadBroker = findNewLeader(leadBroker, port, topic, partitionId);
                continue;
            }
            numErrors = 0;

            long numRead = 0;
            for (MessageAndOffset messageAndOffset : response.messageSet(topic, partitionId)) {
                long currentOffset = messageAndOffset.offset();
                if (currentOffset < readOffset) {
                    System.out.println("Found an old offset: " + currentOffset + " Expecting: " + readOffset);
                    continue;
                }
                readOffset = messageAndOffset.nextOffset();
                ByteBuffer payload = messageAndOffset.message().payload();

                byte[] bytes = new byte[payload.limit()];
                payload.get(bytes);
                System.out.println(String.valueOf(messageAndOffset.offset()) + ": " + new String(bytes, "UTF-8"));
                numRead++;
                maxReads--;
            }
            if (numRead == 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
        }
        if (consumer != null) {
            consumer.close();
        }
    }
}
