package com.example.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

//透過自定義config類、或是在.yml配置自定義CustomConsumerInterceptor

public class CustomConsumerInterceptor implements ConsumerInterceptor<String, String> {

	private static final long TIMEOUT_MS = 10 * 1000;

	@Override
	public void configure(Map<String, ?> configs) {
		// 配置攔截器時執行的操作(如果有的話)
	}

	@Override
	public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
		// 消息消費前的攔截處理
		Map<TopicPartition, List<ConsumerRecord<String, String>>> filteredRecordsMap = new HashMap<>();
		long now = System.currentTimeMillis();
		for (ConsumerRecord<String, String> record : records) {
			System.out.println("處理時間：" + (now - record.timestamp())/1000.0 + "秒，是否超時(" + TIMEOUT_MS/1000.0 + "秒) "
					+ !(now - record.timestamp() <= TIMEOUT_MS));
			if (now - record.timestamp() <= TIMEOUT_MS) {
				// 獲取消息的分區信息
				TopicPartition partition = new TopicPartition(record.topic(), record.partition());

				// 將符合條件的消息添加到分區的記錄列表中
				filteredRecordsMap.computeIfAbsent(partition, k -> new ArrayList<>()).add(record);
			}else {
				System.out.println("超時不處理");
			}
		}

		ConsumerRecords<String, String> filteredRecords = new ConsumerRecords<>(filteredRecordsMap);

		return filteredRecords;
	}

	@Override
	public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
		// 消息提交前的攔截處理
	}

	@Override
	public void close() {
		// 關閉資源或執行清理操作
	}

}
