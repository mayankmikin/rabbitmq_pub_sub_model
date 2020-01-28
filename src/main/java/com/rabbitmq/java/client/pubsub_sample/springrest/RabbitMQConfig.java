package com.rabbitmq.java.client.pubsub_sample.springrest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

	
	String queueName;

	
	String exchange;

	
	private String routingkey;
	
	
	private String host;
	
	
	private String username;
	
	
	private String password;
	
	
	private String vhost;
	
	
	@Autowired
	public RabbitMQConfig(
			@Value("${test.rabbitmq.queue}")String queueName,
			@Value("${test.rabbitmq.exchange}")String exchange,
			@Value("${test.rabbitmq.routingkey}")String routingkey,
			@Value("${spring.rabbitmq.host}")String host,
			@Value("${spring.rabbitmq.username}")String username,
			@Value("${spring.rabbitmq.password}")String password,
			@Value("${spring.rabbitmq.virtual-host}")String vhost) {
		super();
		this.queueName = queueName;
		this.exchange = exchange;
		this.routingkey = routingkey;
		this.host = host;
		this.username = username;
		this.password = password;
		this.vhost = vhost;
	}
	@Bean
    public ConnectionFactory connectionFactory() throws IOException, TimeoutException {
		System.out.println("host is: "+host);
		System.out.println("username is: "+username);
		System.out.println("password is: "+password);
		System.out.println("vhost is: "+vhost);
		CachingConnectionFactory connectionFactory=new CachingConnectionFactory(host);
		connectionFactory.setUsername(username);
		  connectionFactory.setPassword(password);
		  connectionFactory.setVirtualHost(vhost);

		  //Recommended settings
		  connectionFactory.setRequestedHeartBeat(30);
		  connectionFactory.setConnectionTimeout(30000);
//		  RabbitAdmin admin = new RabbitAdmin(connectionFactory);
//		  Queue queue = new Queue(queueName);
//		  admin.declareQueue(queue);
//		  TopicExchange exchangeTopic = new TopicExchange(exchange);
//		  admin.declareExchange(exchangeTopic);
//		  admin.declareBinding(
//		    BindingBuilder.bind(queue).to(exchangeTopic).with(routingkey+".*"));
        return connectionFactory;
    }
    @Bean
    public AmqpAdmin amqpAdmin() throws IOException, TimeoutException {
        return new RabbitAdmin(connectionFactory());
    }
    
	@Bean
	Queue queue() {
		System.out.println("queueName is: "+queueName);
		return new Queue(queueName, true);
	}

	@Bean
	DirectExchange exchange() {
		System.out.println("exchange is: "+exchange);
		return new DirectExchange(exchange);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		String routKey=routingkey+".*";
		System.out.println("routingkey is: "+routKey);
		return BindingBuilder.bind(queue).to(exchange).with(routKey);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	
	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		System.out.println("exchange is: "+exchange);
		rabbitTemplate.setExchange(exchange);
//		rabbitTemplate.setRoutingKey(routingkey+".spring");
		return rabbitTemplate;
	}
}
