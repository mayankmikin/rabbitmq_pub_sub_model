package com.rabbitmq.java.client.pubsub_sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.rabbitmq.java.client.pubsub_sample.Sender;
@SpringBootApplication
@EnableScheduling
public class PubsubSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PubsubSampleApplication.class, args);
		sender();
	}
	public static void sender()
	{
		Sender producer= new Sender();
		producer.send();
	}
}
