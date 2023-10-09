package com.example.consumption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ConsumptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumptionApplication.class, args);
	}

}
