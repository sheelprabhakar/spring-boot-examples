package com.code4copy.rabbitmqexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabbitMqExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitMqExampleApplication.class, args);
	}

}
