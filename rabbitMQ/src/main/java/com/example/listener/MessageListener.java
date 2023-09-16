package com.example.listener;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.stereotype.Component;
import org.springframework.ui.context.Theme;

import com.rabbitmq.client.Channel;

@Component
public class MessageListener {

	// 可以配置多個消費者監聽同一隊列，競爭關係
	@RabbitListener(queues = "helloWorld")
	public void receiveHelloWorld(String id) {
		System.out.println("已完成消息發送業務(rabbitmq helloWorld) consumer1，id：" + id);
	}

	@RabbitListener(queues = "helloWorld")
	public void receiveHelloWorld2(String id) {
		System.out.println("已完成消息發送業務(rabbitmq helloWorld) consumer2，id：" + id);
	}

	// 這邊是自動確認，不做額外簽收處理
//	@RabbitListener(queues = "direct_queue")
//	public void receiveDirect(String id) {
//		System.out.println("已完成消息發送業務(rabbitmq direct) queue1，id：" + id);
//	}

	// 手動簽收
	@RabbitListener(queues = "direct_queue", containerFactory = "myRabbitListenerContainerFactory")
	public void receiveDirect(Message message, Channel channel) throws Exception {
		try {
			// 接收消息
			System.out.println("已完成消息發送業務(rabbitmq direct) queue1，id：" + new String(message.getBody()));

			// 處理業務邏輯
			// ...
//			int i = 1 / 0;
			// 手動確認消息，當然也可以拒絕消息(透過basicAck或basicReject)
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
		} catch (Exception e) {
			// 第三個參數 可選擇是否讓消息重回隊列
			// 發生錯誤時也可以選擇不確認消息，做額外處理
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), true, true);
		}
	}

	@RabbitListener(queues = "direct_queue2")
	public void receiveDirect2(String id) {
		System.out.println("已完成消息發送業務(rabbitmq direct) queue2，id：" + id);
	}
	
	// rabbitMQ中並未提供延遲隊列功能
	// 替代方案是使用TTL(過期)+死信隊列 組合實現延遲功能
	// 這個監聽器就可以收到延遲發送的消息做業務處理
	@RabbitListener(queues = "dead_direct_queue")
	public void receiveDeadDirect(String id) {
		System.out.println("已完成消息發送業務(rabbitmq dead direct) dead queue，id：" + id);
	}

	@RabbitListener(queues = "topic_queue")
	public void receiveTopic(String id) {
		System.out.println("已完成消息發送業務(rabbitmq topic)，id：" + id);
	}

	@RabbitListener(queues = "topic_queue2")
	public void receiveTopic2(String id) {
		System.out.println("已完成消息發送業務(rabbitmq topic2)，id：" + id);
	}

	@RabbitListener(queues = "fanout_queue")
	public void receiveFanout(String id) {
		System.out.println("已完成消息發送業務(rabbitmq fanout)，id：" + id);
	}

	@RabbitListener(queues = "fanout_queue2")
	public void receiveFanout2(String id) {
		System.out.println("已完成消息發送業務(rabbitmq fanout2)，id：" + id);
	}
}
