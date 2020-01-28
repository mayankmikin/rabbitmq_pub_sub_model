package com.rabbitmq.java.client.pubsub_sample.springrest;

public class CustomMessage {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message [message=" + message + "]";
	}

	public CustomMessage(String message) {
		super();
		this.message = message;
	}

	public CustomMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
