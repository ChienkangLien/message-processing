package com.example.service.impl.rabbit.confirm;

import java.util.Iterator;
import java.util.UUID;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service.MessageService;

@Service
public class MessageServiceRabbitImpl implements MessageService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public void sendMessage(String id) {
		System.out.println("待發送消息的訂單已納入處理隊列(rabbitmq direct)，id：" + id);
		for (int i = 0; i < 10; i++) {
			rabbitTemplate.convertAndSend("directExchange", "direct", id, new CorrelationData(UUID.randomUUID().toString()));
		}
	}

	@Override
	public String doMessage() {
		// 導入RabbitMQ後這邊就不動作了，採用監聽器
		return null;
	}
}
