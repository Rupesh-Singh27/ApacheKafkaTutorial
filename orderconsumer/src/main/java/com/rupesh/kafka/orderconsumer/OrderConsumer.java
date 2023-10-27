package com.rupesh.kafka.orderconsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.RangeAssignor;

public class OrderConsumer {

	public static void main(String[] args) {
		
		Properties props = new Properties();
		props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
		props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "OrderGroup");
		props.setProperty(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, "1024121212"); //once message is of this size, then send to consumer
		props.setProperty(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, "200"); //wait for 200 milisecond, before sending data to consumer
		props.setProperty(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "1000"); //every specified milliseconds send heartbeat to group co-ordinator
		props.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "3000"); // tells broker that how long consumer can work without sending heartbeat
		props.setProperty(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "1MB"); //maximum number of bytes the server returns to the consumer and the default is 1MB
		props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); //consumer reads a partition that doesn't have a committed offset. either latest or earliest
		props.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "orderConsumer"); //used for logging metrics etc
		props.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "12"); //maximum number of records the Poll method can return
		props.setProperty(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RangeAssignor.class.getName());
		
		KafkaConsumer<String, Integer> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Collections.singletonList("OrderTopic"));
		
		ConsumerRecords<String, Integer> orders = consumer.poll(Duration.ofSeconds(20));
		
		for (ConsumerRecord<String, Integer> order : orders) {
			System.out.println("Product Name: " + order.key());
			System.out.println("Product Value: " + order.value());
		}
		consumer.close();
	}
}
