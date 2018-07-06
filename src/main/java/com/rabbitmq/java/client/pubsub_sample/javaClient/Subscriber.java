package com.rabbitmq.java.client.pubsub_sample.javaClient;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
public class Subscriber {

	public static void main(String[] args) {
		Properties prop = new Properties();
		InputStream inputProp = null;
		String filename = "config.properties";
		inputProp = Publisher.class.getClassLoader().getResourceAsStream(filename);
		try
		{
			/*	prop.load(inputProp);
				ConnectionFactory factory = new ConnectionFactory();
			    factory.setHost("localhost");
			    Connection connection = factory.newConnection();
			    Channel channel = connection.createChannel();

			    channel.queueDeclare(prop.getProperty("TASK_QUEUE_NAME"), false, false, false, null);
			    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		
			    int prefetchCount = 1;
				channel.basicQos(prefetchCount);
				// channel.basicQos(1);

				final Consumer consumer = new DefaultConsumer(channel) {
					@Override
					public void handleDelivery(String consumerTag,
							Envelope envelope, AMQP.BasicProperties properties,
							byte[] body) throws IOException {
						String message = new String(body, "UTF-8");
						System.out.println(" [x] Received message is: '" + message );
						
						
							
							channel.basicAck(envelope.getDeliveryTag(), false);
							
						
					}
				};

				channel.basicConsume(prop.getProperty("TASK_QUEUE_NAME"), false, consumer); // dequeue	
		*/
			startConsumer();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void startConsumer() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			/*
			 * for local setup comment factory.setUri() and uncomment the below
			 * lines
			 */
			factory.setUri("amqp://iamtest:Admin123@localhost:5672/myvhost");
			final Connection connection = factory.newConnection();
			final Channel channel = connection.createChannel();

			channel.queueDeclare("test_queue", true, false, false, null);
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
			int prefetchCount = 1;
			channel.basicQos(prefetchCount);

			final Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag,
						Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
					System.out.println(" [x] Received '" + message);
						channel.basicAck(envelope.getDeliveryTag(), false);
				}
			};

			channel.basicConsume("test_queue", false, consumer); // dequeue
			
			System.out.println("consumed message ");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
