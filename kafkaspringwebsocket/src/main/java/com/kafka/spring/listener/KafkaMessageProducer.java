package com.kafka.spring.listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kafka.spring.model.Message;
import com.kafka.spring.model.Messages;

@Controller
public class KafkaMessageProducer {
	
	@Autowired
    private KafkaTemplate<String, List<Messages>> kafkaTemplate;
	
	@Autowired
    private KafkaTemplate<String, Message> sendTemplate;

	private static final String TOPIC = "test";
	
	private static final String User_TOPIC = "user_messages";
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	
	@MessageMapping("/message")
    @SendTo("/topic/message")
	public Message greeting(Message message){
    	System.out.println("I am from greetings method" + message);
		sendTemplate.send(TOPIC,message);
		return message;
	}
	
	
	@MessageMapping("/messages")
    @SendTo("/topic/messages")
    //@PostMapping("/publish")
    public List<Messages> post(Messages messages) {
   
	     	System.out.println("I am from greetings method" + messages);
              List<Messages> allrest= new ArrayList<>();
                
              allrest.add(messages);
              
              kafkaTemplate.send(User_TOPIC,allrest);
              
              return allrest;         
      
    }
    
}
