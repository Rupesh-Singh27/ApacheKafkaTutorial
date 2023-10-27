package com.rupesh.kafka.streamsdemo;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;


public class WordCountStream {

	public static void main(String[] args) {
		
		//Step 1: Properties
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-dataflow");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//		props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
		
		//Step 2: Computational Logic or Stream Topology
		StreamsBuilder builder = new StreamsBuilder();
		
		KStream<String, String> streams = builder.stream("streams-wordcount-input");
		
		KGroupedStream<String, String> kGroupedStream = 
				streams.flatMapValues(value -> Arrays.asList(value.toLowerCase().split(" ")))
						.groupBy((key, value) -> value);
		
		KTable<String, Long> countsTable = kGroupedStream.count();
		countsTable.toStream().to("streams-wordcount-output", Produced.with(Serdes.String(), Serdes.Long()));
		
		Topology topology = builder.build();
		System.out.println(topology.describe());
		
		//Step 3: Start Stream
		KafkaStreams kafkaStream = new KafkaStreams(topology, props);
		kafkaStream.start();
		
		//Step 4: Stop Stream
		Runtime.getRuntime().addShutdownHook(new Thread(kafkaStream::close));
	}
}
