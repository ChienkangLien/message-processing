package com.example.interceptor;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

//透過自定義config類、或是在.yml配置自定義CustomProducerInterceptor

public class CustomProducerInterceptor implements ProducerInterceptor<String, String> {

	// 關鍵字volatile
	// 1.可見性保證：在多執行緒環境下，當一個執行緒修改一個 volatile 變量的值時，
	// 這個新值會立即對其他執行緒可見。這意味著其他執行緒不會緩存該變量的舊值，而是會立即看到最新的值。
	// 2.禁止重排序：volatile 變量的讀寫操作不會被重排序到其他指令之前或之後，這有助於確保操作的有序性。
	// 3.不提供原子性：雖然 volatile 提供了可見性和有序性，但它並不能保證覆合操作的原子性。
	// 如果一個操作依賴於變量的當前值，並且多個執行緒都在同時修改該變量，那麽 volatile 不足以確保操作的原子性。

	private volatile long sendSucuccess = 0;
	private volatile long sendFailure = 0;

	@Override
	public void configure(Map<String, ?> configs) {
		// 配置攔截器時執行的操作
	}

	@Override
	public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
		String modifiedValue = "prefix-" + record.value();
		return new ProducerRecord<>(record.topic(), record.partition(), record.timestamp(), record.key(), modifiedValue,
				record.headers());
	}

	@Override
	public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
		if (exception == null) {
			sendSucuccess++;
		} else {
			sendFailure++;
		}
	}

	@Override
	public void close() {
		double successRatio = (double) sendSucuccess / (sendFailure + sendSucuccess);
		System.out.println("[INFO] 發送成功率 = " + String.format("%f", successRatio * 100) + "%");
	}

}
