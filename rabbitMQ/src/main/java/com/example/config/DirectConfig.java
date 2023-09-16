package com.example.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {

	// 簡單模式，不配交換機
	@Bean
	public Queue helloWorldQueue() {
		return new Queue("helloWorld"); // 建構子有多個參數可帶入，durable(持久化), exclusive(專用的), autoDelete(自動刪除)等
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

	// 建構子所帶參數可做更多配置
	// 配置queue中消息的過期時間
	// 配置長度限制
	// 配置死信處理，其條件：過期、超過隊列長度、被拒絕簽收
	/***
	 * @param queue的名稱
	 * @param 是否持久化
	 * @param 是否獨家的
	 * @param 是否自動刪除
	 * @param 更多訊息
	 */
	@Bean
	public Queue directQueue3() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-message-ttl", 100000); //100秒過期
		args.put("x-max-length", 20); //消息隊列長度限制
		args.put("x-dead-letter-exchange", "dead_directExchange"); //指定交換機
		args.put("x-dead-letter-routing-key", "dead_direct"); //配置路由
		return new Queue("direct_queue3", true, false, false, args);
	}
	
	@Bean
	public Queue deadDirectQueue() {
		return new Queue("dead_direct_queue");
	}

	// 一台交換機可處理多個隊列
	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange("directExchange"); // 建構子有多個參數可帶入，durable(持久化), autoDelete(自動刪除)等
	}
	
	// 死信交換機
	@Bean
    public DirectExchange deadDirectExchange() {
        return new DirectExchange("dead_directExchange");
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
	
	@Bean
	public Binding bindingDirect3() {
		return BindingBuilder.bind(directQueue3()).to(directExchange()).with("direct3");
	}
	
	@Bean
	public Binding bindingDeadDirect() {
		return BindingBuilder.bind(deadDirectQueue()).to(deadDirectExchange()).with("dead_direct");
	}

}
