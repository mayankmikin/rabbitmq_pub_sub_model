package com.rabbitmq.java.client.pubsub_sample.springrest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/employee/rabbitmq")
public class RabbitMQWebController {
	@Autowired
	private RabbitMQSender rabbitMQSender;

	@PostMapping(value = "/producer")
	public String producer(@Valid @RequestBody CustomMessage message) {
		rabbitMQSender.send(message);
		System.out.println(message);
		return "Message sent to the RabbitMQ JavaInUse Successfully";
	}
}
