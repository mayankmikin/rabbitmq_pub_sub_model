package com.rabbitmq.java.client.pubsub_sample.javaClient;

import java.io.InputStream;
import java.util.Properties;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Publisher {

	public static void main(String[] args) {
		String message="some text";
		Properties prop = new Properties();
		InputStream inputProp = null;
		String filename = "config.properties";
		inputProp = Publisher.class.getClassLoader().getResourceAsStream(filename);
		try
		{
				prop.load(inputProp);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqp://iamtest:Admin123@localhost:5672/myvhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(prop.getProperty("TASK_QUEUE_NAME"), true, false, false, null);
		channel.basicPublish( "", prop.getProperty("TASK_QUEUE_NAME"),MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes("UTF-8"));
		channel.close();
		connection.close();
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
}

}
