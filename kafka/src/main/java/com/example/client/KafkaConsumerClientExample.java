package com.example.client;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaConsumerClientExample {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
//		String bootstrapServers = "localhost:9092";
		String bootstrapServers = "192.168.191.135:19092";
		String topic = "mytopic1";
		String groupId = "group.demo";

		Properties consumerProps = new Properties();
		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		// groupId用於區分不同的消費者群組。當多個消費者加入同一個groupId時，它們將被視為屬於同一個群組，並且每個分區的消息將只會被群組中的一個消費者消費。
		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

		try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps)) {
			consumer.subscribe(Collections.singletonList(topic));

			// 拉取消息
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
				records.forEach(record -> {
					System.out.printf("Consumed record with key %s and value %s%n", record.key(), record.value());
				});
			}
		}
	}
}
