package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class TopicConfig {
	@Bean
	public Queue topicQueue() {
		return new Queue("topic_queue"); //建構子有多個參數可帶入，durable(持久化), exclusive(專用的), autoDelete(自動刪除)等
	}
	
	@Bean
	public Queue topicQueue2() {
		return new Queue("topic_queue2");
	}
	
	// 一台交換機可處理多個隊列
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange("topicExchange"); //建構子有多個參數可帶入，durable(持久化), autoDelete(自動刪除)等
	}
	
	// 匹配規則：*(星號)表示一個單詞且必須出現、#(井號)表示任意數量
	@Bean
	public Binding bindingTopic() {
		return BindingBuilder.bind(topicQueue()).to(topicExchange()).with("topic.*.id");
	}
	
	@Bean
	public Binding bindingTopic2() {
		return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.order.*");
	}
}
