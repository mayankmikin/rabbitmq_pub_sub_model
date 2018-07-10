package com.rabbitmq.java.client.pubsub_sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
@EnableScheduling
public class PubsubSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PubsubSampleApplication.class, args);
	}

}
