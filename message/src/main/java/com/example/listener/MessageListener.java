package com.example.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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
	
	@RabbitListener(queues = "direct_queue")
	public void receiveDirect(String id) {
		System.out.println("已完成消息發送業務(rabbitmq direct) queue1，id：" + id);
	}
	
	@RabbitListener(queues = "direct_queue2")
	public void receiveDirect2(String id) {
		System.out.println("已完成消息發送業務(rabbitmq direct) queue2，id：" + id);
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
