package com.example.client;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaProducerClientExample {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
//		String bootstrapServers = "localhost:9092";
		String bootstrapServers = "192.168.191.134:19092";
		String topic = "mytopic1";

		Properties producerProps = new Properties();
		producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		// 若要自定義序列化器，需實做Serializer<>
		producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		// 也可自定義分區器，實做Partitioner
//		producerProps.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class);
		
		try (KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps)) {
			// 發送消息
			String messageValue = "I am ProducerClient";
			producer.send(new ProducerRecord<>(topic, messageValue)).get();
			System.out.println("Message sent successfully: " + messageValue);
		}
	}
}
