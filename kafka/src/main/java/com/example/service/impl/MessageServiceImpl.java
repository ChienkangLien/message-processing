package com.example.service.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	// KafkaProducer是更底層的Kafka生產者API，需要手動管理和配置(生命周期、
	// 創建ProducerRecord對象來表示要發送的消息)，適用於那些需要更多控制的場景。
	// 而KafkaTemplate是Spring Kafka提供的高級抽象，簡化了與Kafka的集成，適用於使用Spring框架的應用程序
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Override
	@Transactional // 開啟事務(.yml也須配置)
	public void sendMessage(String id) {
		System.out.println("待發送消息的訂單已納入處理隊列(kafka)，id：" + id);

//		kafkaTemplate.send("mytopic", id); // send方法可帶多個參數做進一步指定(partition、key等)

		// 默認是異步的，而可以使用CompletableFuture<SendResult<K, V>>來獲取返回值，
		// 並在需要時調用get()方法等待結果。這將使發送變為同步操作，但請注意，
		// 如果發送失敗或發生錯誤，get()方法可能會阻塞，直到發送完成或超時。
		CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("mytopic2", id);

		// 指定timestamp(CustomConsumerInterceptor測試用)
//		CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("mytopic2", null, System.currentTimeMillis()-10 * 1000, "key", id);
		try {
			System.out.println(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		// 若非要獲取結果並保持異步操作，則採以下方式
		future.thenAccept(result -> {
			System.out.println("Message sent successfully: " + result);
		}).exceptionally(ex -> {
			System.err.println("Failed to send message: " + ex.getMessage());
			return null;
		});
	}

	@Override
	public String doMessage() {
		return null;
	}

}
