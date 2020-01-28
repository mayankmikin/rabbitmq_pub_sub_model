package com.rabbitmq.java.client.pubsub_sample.springrest;

import java.io.IOException;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RabbitMQSender {

	private ObjectMapper jsonObjectMapper = new ObjectMapper();
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${test.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${test.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void send(CustomMessage message) {
		byte[] bytes = null;
		try {
			String jsonString = jsonObjectMapper
					.writeValueAsString(message);
			bytes = jsonString.getBytes();
			//rabbitTemplate.convertAndSend(exchange, routingkey+".bar", bytes);
		}
		catch (IOException e) {
			throw new MessageConversionException(
					"Failed to convert Message content", e);
		}
		//System.out.println(rabbitTemplate.toString());
		//rabbitTemplate.convertAndSend("dcs_report_exchange", "dcs_report_exchange.bar", message);
		rabbitTemplate.convertAndSend("dcs_report_exchange", "dcs_report_exchange_key.spring", message);
		System.out.println("Send msg = " + message.getMessage());
	    
	}
}
