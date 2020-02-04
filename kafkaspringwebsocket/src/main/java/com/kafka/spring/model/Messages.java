package com.kafka.spring.model;

import java.util.Map;


public class Messages {
	
	Map<String,Message[]> messages;
	
	public Messages() {
		
	}

	public Messages(Map<String, Message[]> messages) {
		this.messages = messages;
	}

	
	@Override
	public String toString() {
		return "Messages [" + messages + "]";
	}

	public Map<String, Message[]> getMessages() {
		return messages;
	}

	public void setMessages(Map<String, Message[]> messages) {
		this.messages = messages;
	}
	
	
	

}
