package com.example.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.example.interceptor.CustomProducerInterceptor;

// 透過自定義config類、或是在.yml配置自定義CustomProducerInterceptor

//@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        // ProducerFactory 配置
    	Map<String, Object> configProps = new HashMap<>();
    	// 必要
    	// bootstrap.servers（必須）：Kafka 服務器的地址列表，用於連接到 Kafka 集群
    	configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    	// key.serializer（必須）：消息鍵的序列化器類
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // value.serializer（必須）：消息值的序列化器類
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        
        // 可選
        // acks：消息確認模式，指定消息被寫入 Kafka 後需要多少個副本確認。
        // 可選值包括 "0"（無確認，性能最高）、"1"（Leader 副本確認）和 
        // "all"（所有副本確認，最高可靠性）。默認為 "1"
        configProps.put(ProducerConfig.ACKS_CONFIG, "1"); // 消息確認模式
        // retries：消息發送失敗後的重試次數。默認為 0，不進行重試。
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3); // 重試次數
        // batch.size：控制每個分區的消息批次大小，以字節為單位。默認為 16384 字節（16 KB）
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384); // 批次大小
        // linger.ms：消息發送的最大等待時間，控制在發送消息前等待更多消息加入批次。
        // 默認為 0 毫秒，即立即發送
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 1); // 發送前等待時間
        // buffer.memory：Kafka 生產者可用於緩沖消息的內存大小，默認為 33554432 字節（32 MB）
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432); // 緩沖內存
        // compression.type：消息壓縮類型，可選值包括 "none"、"gzip"、"snappy"
        // 和 "lz4" 等。默認為 "none"
        configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy"); // 壓縮類型
        // max.in.flight.requests.per.connection：控制在同一連接上允許多少未確認的請求，默認為 5
        configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5); // 最大未確認請求
        // client.id：Kafka 客戶端的唯一標識，用於日志和監控
        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, "your-client-id"); // 客戶端ID
        configProps.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, CustomProducerInterceptor.class.getName()); // 自訂攔截器
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        // 註冊自定義 ProducerInterceptor
        kafkaTemplate.setProducerInterceptor(new CustomProducerInterceptor());
        return kafkaTemplate;
    }
}
