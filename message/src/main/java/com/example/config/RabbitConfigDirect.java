package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigDirect {
	
	// 簡單模式，不配交換機
	@Bean
	public Queue helloWorldQueue() {
		return new Queue("helloWorld"); //建構子有多個參數可帶入，durable(持久化), exclusive(專用的), autoDelete(自動刪除)等
	}
	
	// 路由模式，配置交換機來做路由指定(多對一)
	@Bean
	public Queue directQueue() {
		return new Queue("direct_queue");
	}
	
	@Bean
	public Queue directQueue2() {
		return new Queue("direct_queue2");
	}
	
	// 一台交換機可處理多個隊列
	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange("directExchange"); //建構子有多個參數可帶入，durable(持久化), autoDelete(自動刪除)等
	}
	
	@Bean
	public Binding bindingDirect() {
		return BindingBuilder.bind(directQueue()).to(directExchange()).with("direct");
	}
	
	@Bean
	public Binding bindingDirect2() {
		return BindingBuilder.bind(directQueue2()).to(directExchange()).with("direct2");
	}
	
	@Bean
	public Binding bindingDirect2_1() {
		return BindingBuilder.bind(directQueue2()).to(directExchange()).with("direct2_1");
	}
	
}
