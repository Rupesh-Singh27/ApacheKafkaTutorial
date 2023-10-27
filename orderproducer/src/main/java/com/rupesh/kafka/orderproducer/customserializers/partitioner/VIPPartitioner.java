package com.rupesh.kafka.orderproducer.customserializers.partitioner;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

public class VIPPartitioner implements Partitioner {

	@Override
	public void configure(Map<String, ?> configs) {
		// Additional configuration information in order to help our partition logic.

	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		// We often just implement this method
		
		//Given the topic, will return all the partition available for the same.
		List<PartitionInfo> partitions = cluster.availablePartitionsForTopic(topic); 
		
		if(key.toString().equals("Rupesh")) {
			return 7;
		}
		
		//Default logic for partitioning, same as how kafka broker does the partitioning. Uses murmur2 algo from Utils.
		return Math.abs(Utils.murmur2(keyBytes) % partitions.size() - 1);
	}

	@Override
	public void close() {
		// If you want to close any resources, we open during partition logic

	}

}
