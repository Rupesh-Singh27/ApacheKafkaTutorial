package com.rupesh.kafka.orderproducer.customserializers;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.rupesh.kafka.orderproducer.customserializers.partitioner.VIPPartitioner;

public class OrderProducer {

	public static void main(String[] args) {
		
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092");
		props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.setProperty("value.serializer", "com.rupesh.kafka.orderproducer.customserializers.OrderSerializer");
		props.setProperty("partitioner.class", VIPPartitioner.class.getName());
		
		KafkaProducer<String, Order> producer = new KafkaProducer<String, Order>(props);
		
		Order order = new Order();
		
		order.setCustomerName("Singh");
		order.setProduct("IPhone");
		order.setQuantity(1);
		
		ProducerRecord<String, Order> record = 
				new ProducerRecord<>("OrderPartitionedTopic", order.getCustomerName(), order);

		try {
			producer.send(record);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			producer.close();
		}
	}
}
