package com.kafka.spring.config;


import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.kafka.spring.model.Message;
import com.kafka.spring.model.Messages;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	
	@Bean
	public ConsumerFactory<String,Message> consumerFactory(){
		Map<String,Object> config =new HashMap<>();
		
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);
		
		return new DefaultKafkaConsumerFactory<String,Message>(config, new StringDeserializer(),
                new JsonDeserializer<>(Message.class));
		
	}
	
	
	@Bean 
	public ConcurrentKafkaListenerContainerFactory<String,Message> kafkaListenerContainerFactory() {
		
		ConcurrentKafkaListenerContainerFactory<String,Message> factory = new ConcurrentKafkaListenerContainerFactory<String,Message>();
		
		factory.setConsumerFactory(consumerFactory());
		
		return factory;
	}
	
	
	@Bean 
	public ConsumerFactory<String,Messages> messageConsumerFactory(){
		
      Map<String,Object> config =new HashMap<>();
		
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(Messages.class));
		
	}

	@Bean 
	public ConcurrentKafkaListenerContainerFactory<String,Messages> messagekafkaListenerContainerFactory() {
		
		ConcurrentKafkaListenerContainerFactory<String,Messages> factory = new ConcurrentKafkaListenerContainerFactory<>();
		
		factory.setConsumerFactory(messageConsumerFactory());
		
		return factory;
	}

}
