package com.rupesh.kafka.streamsdemo;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;


public class DataFlowStream {

	public static void main(String[] args) {
		
		//Step 1: Properties
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-dataflow");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		
		//Step 2: Computational Logic or Stream Topology
		StreamsBuilder builder = new StreamsBuilder();
		
		KStream<String, String> stream = builder.stream("streams-dataflow-input");
		
		stream.foreach((key, value) -> System.out.println("key and value " + key +" "+ value));
		stream.filter((key, value)->value.contains("token"))
//				.mapValues(value -> value.toUpperCase())
//				.map((key, value)->new KeyValue<>(key, value.toUpperCase()))
				.map((key, value)->KeyValue.pair(key,value.toUpperCase()))
				.to("streams-dataflow-output"); //will write to this topic
		
		Topology topology = builder.build();
		
		System.out.println(topology.describe());
		
		//Step 3: Start Stream
		KafkaStreams kafkaStream = new KafkaStreams(topology, props);
		kafkaStream.start();
		
		//Step 4: Stop Stream
		Runtime.getRuntime().addShutdownHook(new Thread(kafkaStream::close));
	}
}
