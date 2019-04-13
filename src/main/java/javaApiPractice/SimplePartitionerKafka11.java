package javaApiPractice;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class SimplePartitionerKafka11 implements Partitioner {


    @Override
    public int partition(final String topic, final Object key, final byte[] keyBytes, final Object value, final byte[] valueBytes, final Cluster cluster) {
        return ((int)(Integer.toUnsignedLong(key.hashCode()) % cluster.availablePartitionsForTopic(topic).size()));
    }

    @Override
    public void close() {
        // Do nothing
    }

    @Override
    public void configure(Map<String, ?> configs) {
        // Do nothing
    }
}
