package com.example.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnsCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	@Autowired
	private ConnectionFactory connectionFactory;

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

		// yml開啟publisher-confirm-type: correlated
		// 交換機接收消息，會做通知
		// correlationData 相關配置訊息
		// ack 交換機是否收到消息
		// cause 失敗原因
		rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
			if (ack) {
				System.out.println("生產端發送至交換機成功: " + correlationData.getId());
			} else {
				System.out.println("生產端發送至交換機失敗: " + correlationData.getId() + " with " + cause);
			}
		});

		// yml開啟publisher-returns: true
		// 消息從交換機到隊列之間失敗的話會執行
		// message 消息
		// replyCode 錯誤碼
		// replyText 錯誤訊息
		// exchange 交換機
		// routingKey 路由鍵
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setReturnsCallback(new ReturnsCallback() {
			@Override
			public void returnedMessage(ReturnedMessage returned) {
				System.out.println(returned);
				System.out.println("交換機到隊列時發生錯誤...");
			}
		});

		return rabbitTemplate;
	}
	
	@Bean(name = "myRabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory2(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 開啟消費端手動處理
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        
        // 設置一次處理數量為 3 條消息
        // 消費端限流，一定要開啟手動處理才有作用
        factory.setPrefetchCount(3);

        return factory;
    }
}
