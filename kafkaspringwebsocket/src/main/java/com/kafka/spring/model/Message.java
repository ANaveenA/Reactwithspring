package com.kafka.spring.model;

import java.util.Date;

public class Message {
	
	private int id;
	
	private String name;
	
	private String date;

    public Message() {
		
			
    }

	
	public Message(int id, String name, String date) {
		this.id = id;
		this.name = name;
		this.date = date;
	}


	@Override
	public String toString() {
		return "Message [id=" + id + ", name=" + name + ", date=" + date + "]";
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String dateformat) {
		this.date = dateformat;
	}
	
	

}
