package com.kafka.spring.listener;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.kafka.spring.model.Message;
import com.kafka.spring.model.Messages;

@Service
public class KafkaMessageConsumer {
	
	@Autowired
    private SimpMessagingTemplate template;
	
	@Autowired
    private SimpMessagingTemplate sendTemplate;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@KafkaListener(topics="test", groupId = "group_id",containerFactory="kafkaListenerContainerFactory")
	public void consumes(Message message) {
	   
		 System.out.println("testing from Kafka:" + message);
	     
		 this.sendTemplate.convertAndSend("/topic/consumeMessage",message);
	}
	
	
	 @KafkaListener(topics = "user_messages", groupId = "group_json",
	                containerFactory = "messagekafkaListenerContainerFactory")
	    public void consumeJson(Messages messages) {
		 
            this.template.convertAndSend("/topic/pushNotification",messages);
		    
	        System.out.println("Consumed JSON : " + messages);
	    }
	
}
