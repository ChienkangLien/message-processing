package com.example.client;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.KafkaFuture;

public class KafkaAdminClientExample {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		// Kafka 服務器地址
		String bootstrapServers = "localhost:9092";

		// 配置 AdminClient
		Properties adminClientProps = new Properties();
		adminClientProps.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

		try (AdminClient adminClient = AdminClient.create(adminClientProps)) {
			// 1. 列出所有主題
			ListTopicsResult listTopicsResult = adminClient.listTopics();
			KafkaFuture<Collection<TopicListing>> topics = listTopicsResult.listings();
			System.out.println("List of topics:");
			topics.get().forEach(topicListing -> System.out.println(topicListing.name()));

			// 2. 創建一個新主題
//			NewTopic newTopic = new NewTopic("mytopic3", 2, (short) 1);
//			CreateTopicsResult createTopicsResult = adminClient.createTopics(Collections.singletonList(newTopic));
//			createTopicsResult.all().get();
//
//			System.out.println("New topic created: new-topic");

			// 3. 刪除一個主題
//			DeleteTopicsResult deleteTopicsResult = adminClient
//					.deleteTopics(Collections.singletonList("topic-to-delete"));
//			deleteTopicsResult.all().get();
//
//			System.out.println("Topic deleted: topic-to-delete");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
