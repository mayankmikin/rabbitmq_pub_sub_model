package com.rabbitmq.java.client.pubsub_sample.springrest;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
public class RabbitMQSubscriber {
	
//	@RabbitListener(queues="${test.rabbitmq.queue}")
//    public void recievedMessage(CustomMessage msg) {
//        System.out.println("Recieved Message: " + msg);
//    }
}
