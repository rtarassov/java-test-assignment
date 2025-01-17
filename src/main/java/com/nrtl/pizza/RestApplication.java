package com.nrtl.pizza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class RestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppConfiguration.class, args);
	}

	@SpringBootApplication
	public static class AppConfiguration {
	}
}
