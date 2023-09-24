package com.example.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

	// @KafkaListener 是一個高級抽象，更適合於快速、自動化地在 Spring 應用程序中啟動 Kafka 消費者。
	// 而 KafkaConsumer 則提供了更多的控制權(需手動管理)，適用於需要自定義配置和處理邏輯的情況
//	@KafkaListener(topics = "mytopic")
	public void onMessage(ConsumerRecord<String, String> record, Acknowledgment ack) {
		String topic = record.topic();
		int partition = record.partition();
		String key = record.key();
		String value = record.value();
		long offset = record.offset();
		System.out.println("已完成消息發送業務(kafka)，topic " + topic + ", partition " + partition + ", offset " + offset
				+ ", key " + key + ", id: " + value);

		// 手動提交需在.yml關閉默認的自動提交及配置ack-mode並加上Acknowledgment參數，如果沒有ack.acknowledge();
		// 1.消息不會被確認：如果消費者在處理消息後沒有手動提交偏移量，Kafka 服務器將認為消息尚未被成功處理。
		// 這意味著 Kafka會將未確認的消息視為未被處理，並在下次重新平衡時將其重新分配給其他消費者。
		// 2.消息重覆處理：由於消息沒有被確認，Kafka 將假定消息尚未被消費者處理。
		// 因此，當消費者重新加入消費者組或發生重新平衡時，未確認的消息可能會被重新分配給消費者，導致消息重覆處理。
		// 3.消費者組偏移量不更新：消費者組的偏移量將保持不變，因為消費者沒有提交成功處理的消息的偏移量。
		// 這可能導致問題，例如消息丟失或消息重覆。

		// 提交偏移量(屬於同步提交)
		ack.acknowledge();
	}

	@KafkaListener(topics = "mytopic")
	public void onMessage(ConsumerRecord<String, String> record, KafkaConsumer<String, String> consumer) {
		String topic = record.topic();
		int partition = record.partition();
		String key = record.key();
		String value = record.value();
		long offset = record.offset();
		System.out.println("已完成消息發送業務(kafka)，topic " + topic + ", partition " + partition + ", offset " + offset
				+ ", key " + key + ", id: " + value);
		// 同步提交
//		consumer.commitSync();
		
		// 異步提交
		consumer.commitAsync();
	}

}
