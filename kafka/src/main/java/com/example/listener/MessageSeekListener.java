package com.example.listener;

import java.util.Collection;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
@Component
public class MessageSeekListener implements ConsumerSeekAware {
	
//	@KafkaListener(topics = "mytopic2")
    public void onMessage(ConsumerRecord<String, String> record, Acknowledgment ack) {
		String topic = record.topic();
		int partition = record.partition();
		String key = record.key();
		String value = record.value();
		long offset = record.offset();
		System.out.println("已完成消息發送業務(kafka)，topic " + topic + ", partition " + partition + ", offset " + offset
				+ ", key " + key + ", id: " + value);
		
        ack.acknowledge();
    }
	
    @Override
    public void registerSeekCallback(ConsumerSeekCallback callback) {
    	// 注冊自定義的 ConsumerSeekCallback 回調
    	// 當消費者需要重新定位（seek）偏移量時，ConsumerSeekCallback 回調會被觸發
    	System.out.println("Custom seek callback registered.");
    }
    
    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        // 指定偏移量，並從該偏移量開始提取
//        assignments.keySet().forEach(partition -> callback.seek("mytopic2", partition.partition(), 30L));
        assignments.keySet().forEach(partition -> callback.seekToBeginning("mytopic2", partition.partition()));
    }

    @Override
    public void onIdleContainer(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
  		// 在container空閒時執行一些操作，例如定期觸發 seek 操作
    }
    
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        // 在分區被撤銷分配時執行操作，例如保存偏移量
    }
}
