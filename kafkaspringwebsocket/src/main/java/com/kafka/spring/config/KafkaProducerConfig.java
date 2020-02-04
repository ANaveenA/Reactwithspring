package com.kafka.spring.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.kafka.spring.model.Message;
import com.kafka.spring.model.Messages;

@Configuration
public class KafkaProducerConfig {
	
	@Bean 
	public ProducerFactory<String,Message>  producerSendFactory() {
		
	   Map<String, Object> sendConfig = new HashMap<>();
	   
	   sendConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
	   
	   sendConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	   
	   sendConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,JsonSerializer.class);
		
	   return new DefaultKafkaProducerFactory<>(sendConfig);
	}
	
	 @Bean
     public KafkaTemplate<String,Message> kafkaSendTemplate() {
	     return new KafkaTemplate<>(producerSendFactory());
	 }
	 
	
	@Bean 
	public ProducerFactory<String,List<Messages>>  producerFactory() {
		
	   Map<String, Object> config = new HashMap<>();
	   
	   config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
	   
	   config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	   
       config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
	   return new DefaultKafkaProducerFactory<>(config);
	}
	
	    @Bean
	    public KafkaTemplate<String, List<Messages>> kafkaTemplate() {
	        return new KafkaTemplate<>(producerFactory());
	    }
	 

}
