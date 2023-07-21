package com.citi.copliot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CopliotApplication {

	public static void main(String[] args) {
		SpringApplication.run(CopliotApplication.class, args);
	}
	//generate a random number between 1 and 100
	public static int generateRandomNumber() {
		return (int) (Math.random() * 100 + 1);
	}


}
