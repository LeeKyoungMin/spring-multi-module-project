package com.jobis.refund;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "application, application-user, application-common, application-repository, application-config");
		SpringApplication.run(UserApplication.class, args);
	}

}
