package com.cyhee.rabit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class RabitApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabitApplication.class, args);
	}
}
