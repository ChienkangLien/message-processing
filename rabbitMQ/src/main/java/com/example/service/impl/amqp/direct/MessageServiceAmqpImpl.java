package com.example.service.impl.amqp.direct;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service.MessageService;
//@Service
public class MessageServiceAmqpImpl implements MessageService {
	
	// AmqpTemplate算是規範、RabbitTemplate而是進一步實現
	// 此概念好比JPA與Hibernate
	@Autowired
	private AmqpTemplate amqpTemplate;

	@Override
	public void sendMessage(String id) {
		System.out.println("待發送消息的訂單已納入處理隊列(rabbitmq direct)，id：" + id);
		
//		amqpTemplate.convertAndSend( "helloWorld", id); 範例用
		
		amqpTemplate.convertAndSend("directExchange", "direct", id);
		
		// 路由模式，會對到同一隊列
		amqpTemplate.convertAndSend("directExchange", "direct2", id);
		amqpTemplate.convertAndSend("directExchange", "direct2_1", id);
	}

	@Override
	public String doMessage() {
		// 導入RabbitMQ後這邊就不動作了，採用監聽器
		return null;
	}

}
