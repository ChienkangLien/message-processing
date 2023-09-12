package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigFanout {

	@Bean
	public Queue fanoutQueue() {
		return new Queue("fanout_queue"); //建構子有多個參數可帶入，durable(持久化), exclusive(專用的), autoDelete(自動刪除)等
	}
	
	@Bean
	public Queue fanoutQueue2() {
		return new Queue("fanout_queue2");
	}
	
	// 一台交換機可處理多個隊列
	@Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange"); //建構子有多個參數可帶入，durable(持久化), autoDelete(自動刪除)等
    }
	
	// 不用聲明Queue名稱，推播作用通發消息
	@Bean
	public Binding bindingFanout() {
		return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
	}
	
	@Bean
	public Binding bindingFanout2() {
		return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
	}
}
