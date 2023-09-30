package com.example.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

	// @KafkaListener 是一個高級抽象，更適合於快速、自動化地在 Spring 應用程序中啟動 Kafka 消費者。
	// 註解中可做進一步指定(partition、group等)
	// 而 KafkaConsumer 則提供了更多的控制權(需手動管理)，適用於需要自定義配置和處理邏輯的情況
	@KafkaListener(topics = "mytopic2", topicPartitions = @TopicPartition(topic = "mytopic2", partitions = { "0" }))
	public void onMessage(ConsumerRecord<String, String> record, Acknowledgment ack) {
		String topic = record.topic();
		int partition = record.partition();
		String key = record.key();
		String value = record.value();
		long offset = record.offset();
		System.out.println("1已完成消息發送業務(kafka)，topic " + topic + ", partition " + partition + ", offset " + offset
				+ ", key " + key + ", id: " + value);

		// 手動提交需在.yml關閉默認的自動提交及配置ack-mode並加上Acknowledgment參數，如果沒有ack.acknowledge();
		// (自動提交的話不需加上Acknowledgment參數、也不須ack.acknowledge())
		// 1.消息不會被確認：如果消費者在處理消息後沒有手動提交偏移量，Kafka 服務器將認為消息尚未被成功處理。
		// 這意味著 Kafka會將未確認的消息視為未被處理，並在下次重新平衡時將其重新分配給其他消費者。
		// 2.消息重覆處理：由於消息沒有被確認，Kafka 將假定消息尚未被消費者處理。
		// 因此，當消費者重新加入消費者組或發生重新平衡時，未確認的消息可能會被重新分配給消費者，導致消息重覆處理。
		// 3.消費者組偏移量不更新：消費者組的偏移量將保持不變，因為消費者沒有提交成功處理的消息的偏移量。
		// 這可能導致問題，例如消息丟失或消息重覆。

		// 提交偏移量(屬於同步提交)
		ack.acknowledge();
	}

//	@KafkaListener(topics = "mytopic2")
	@KafkaListener(topics = "mytopic2", topicPartitions = @TopicPartition(topic = "mytopic2", partitions = { "1" }))
	public void onMessage(ConsumerRecord<String, String> record, KafkaConsumer<String, String> consumer) {
		String topic = record.topic();
		int partition = record.partition();
		String key = record.key();
		String value = record.value();
		long offset = record.offset();
		System.out.println("2已完成消息發送業務(kafka)，topic " + topic + ", partition " + partition + ", offset " + offset
				+ ", key " + key + ", id: " + value);

		// 若提交不成功或是發生異常（或者再均衡），會發生上述的意外

		// 同步提交
//		consumer.commitSync();

		// 異步提交
		consumer.commitAsync();
	}

}

//異步提交（Asynchronous Commit）：
//優點：
//更高的吞吐量： 異步提交不會阻塞消費者線程，允許消費者在提交偏移量的同時繼續消費消息，從而提高了吞吐量。
//較低的延遲： 由於不需要等待提交操作完成，因此異步提交通常具有較低的延遲。
//
//缺點：
//不確定性： 異步提交是非阻塞的，所以無法確保提交一定會成功。如果提交失敗，可能會導致偏移量不正確，需要額外的邏輯來處理提交失敗的情況。
//難以追蹤錯誤： 如果發生提交失敗或偏移量不正確的情況，難以追蹤和排查問題。
//
//同步提交（Synchronous Commit）：
//優點：
//偏移量準確： 同步提交確保提交偏移量的成功，因此偏移量通常更準確。
//容易追蹤錯誤： 同步提交是阻塞的，如果提交失敗，會立即拋出異常，因此可以更容易地追蹤和處理錯誤。
//
//缺點：
//較低的吞吐量： 同步提交會阻塞消費者線程，直到提交操作完成，這可能會降低消費者的吞吐量。
//較高的延遲： 由於同步提交需要等待提交操作完成，因此通常具有較高的延遲。
