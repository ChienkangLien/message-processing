package com.example.service.impl.rabbit.confirm;

import java.util.UUID;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
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
			rabbitTemplate.convertAndSend("directExchange", "direct2", id,
					new CorrelationData(UUID.randomUUID().toString()));
		}

		// 針對消息本身配置過期時間
		// 注意：消息本身到達隊列頂端才會判斷是否過期，若過期時還在排隊中仍會存在隊列直到輪到頂端後移除
		MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {

			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				// 設置消息的信息
				message.getMessageProperties().setExpiration("5000");
				// 返回該消息
				return message;
			}
		};

		for (int i = 0; i < 10; i++) {
			rabbitTemplate.convertAndSend("directExchange", "direct3", id+"1", messagePostProcessor,
					new CorrelationData(UUID.randomUUID().toString()));
		}

		// direct3有配置10秒過期時間、且長度限制20
		for (int i = 0; i < 20; i++) {
			rabbitTemplate.convertAndSend("directExchange", "direct3", id+"2",
					new CorrelationData(UUID.randomUUID().toString()));
		}
	}

	@Override
	public String doMessage() {
		// 導入RabbitMQ後這邊就不動作了，採用監聽器
		return null;
	}
}
