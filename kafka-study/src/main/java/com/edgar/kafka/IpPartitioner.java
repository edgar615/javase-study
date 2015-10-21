package com.edgar.kafka;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 * Created by edgar on 14-10-22.
 */
public class IpPartitioner implements Partitioner {

    public IpPartitioner(VerifiableProperties props) {
    }

    @Override
    public int partition(Object key, int numPartitions) {
        int partition = 0;
        String stringKey = (String) key;
        int offset = stringKey.lastIndexOf('.');
        if (offset > 0) {
            partition = Integer.parseInt( stringKey.substring(offset+1)) % numPartitions;
        }
        System.out.println("partition" + partition);
        return partition;
    }
}
