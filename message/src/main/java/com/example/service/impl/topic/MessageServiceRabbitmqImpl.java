package com.example.service.impl.topic;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service.MessageService;
//@Service
public class MessageServiceRabbitmqImpl implements MessageService {
	
	@Autowired
	private AmqpTemplate amqpTemplate;

	@Override
	public void sendMessage(String id) {
		System.out.println("待發送消息的訂單已納入處理隊列(rabbitmq topic)，id：" + id);
		amqpTemplate.convertAndSend("topicExchange", "topic.order.id", id);
	}

	@Override
	public String doMessage() {
		// 導入RabbitMQ後這邊就不動作了，採用監聽器
		return null;
	}

}
