package com.example.listener;

import java.util.Collection;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.stereotype.Component;

@Component
public class RebalanceListener implements ConsumerAwareRebalanceListener {

	@Override
	public void onPartitionsRevokedBeforeCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
		System.out.println("onPartitionsRevokedBeforeCommit...");
		// 在分區被撤銷前提交偏移量
		consumer.commitSync();

		// or
		// 暫停消息消費
//		consumer.pause(partitions);
	}

	@Override
	public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        System.out.println("onPartitionsAssigned...");
		// 在分區被重新分配時執行操作
		
        // 繼續消息消費
//        consumer.resume(partitions);
    }
}

//再均衡原因
//消費者加入或離開消費者組。
//主題的分區數量發生變化。
//消費者組的消費者數量發生變化。
