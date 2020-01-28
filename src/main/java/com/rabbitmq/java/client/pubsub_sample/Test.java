package com.rabbitmq.java.client.pubsub_sample;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;

public class Test {

	@Value("${test.rabbitmq.queue}")
	String queueName;

	@Value("${test.rabbitmq.exchange}")
	String exchange;

	@Value("${test.rabbitmq.routingkey}")
	private String routingkey;
	
	@Value("${spring.rabbitmq.host}")
	private String host;
	
	@Value("${spring.rabbitmq.username}")
	private String username;
	
	@Value("${spring.rabbitmq.password}")
	private String password;
	
	@Value("${spring.rabbitmq.virtual-host}")
	private String vhost;
	
	public static void main(String[] args) {
		Test t= new Test();
		CachingConnectionFactory connectionFactory=new CachingConnectionFactory("hawk-01.rmq.cloudamqp.com");
		  connectionFactory.setUsername("metfordz");
		  connectionFactory.setPassword("9rCKBTtxrhdmAxtnDrZ99Pr8qq9yB3lN");
		  connectionFactory.setVirtualHost("metfordz");

		  //Recommended settings
		  connectionFactory.setRequestedHeartBeat(30);
		  connectionFactory.setConnectionTimeout(30000);

		  //Set up queue, exchanges and bindings
		  RabbitAdmin admin = new RabbitAdmin(connectionFactory);
		  Queue queue = new Queue("dcs_report");
		  admin.declareQueue(queue);
		  DirectExchange exchange = new DirectExchange("dcs_report_exchange");
		  admin.declareExchange(exchange);
		  admin.declareBinding(
		    BindingBuilder.bind(queue).to(exchange).with("dcs_report_exchange_key.*"));

		  //Set up the listener
		  SimpleMessageListenerContainer container =
		    new SimpleMessageListenerContainer(connectionFactory);
		  Object listener = new Object() {
		    public void handleMessage(String foo) {
		      System.out.println(foo);
		    }
		  };

		  //Send a message
		  MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
		  container.setMessageListener(adapter);
		  container.setQueueNames("dcs_report");
		  container.start();

		  RabbitTemplate template = new RabbitTemplate(connectionFactory);
		  template.convertAndSend("dcs_report_exchange", "dcs_report_exchange_key.bar", "Hello CloudAMQP!");
		  try{
		    Thread.sleep(1000);
		  } catch(InterruptedException e) {
		     Thread.currentThread().interrupt();
		  }
		  container.stop();

	}

}
