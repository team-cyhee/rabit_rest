package com.cyhee.rabit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.cyhee.rabit.user.exception.NoSuchUserException;
import com.cyhee.rabit.user.model.User;
import com.cyhee.rabit.user.service.BasicUserService;
import com.cyhee.rabit.user.service.UserService;

@SpringBootApplication
public class RabitApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RabitApplication.class, args);
		
		List<User> users = new ArrayList<>();
		users.add(userGenerator("user1"));
		users.add(userGenerator("user2"));

		UserService userService = (UserService)context.getBean(BasicUserService.class);
		
		for (User user : users) {			
			try {
				userService.getUserByUsername(user.getUsername());
			} catch (NoSuchUserException e) {
				userService.addUser(user);
			}
		}
	}
	
	public static User userGenerator(String username) {
		User user = new User();
		user.setEmail(username+"@rabit.com");
		user.setUsername(username);
		user.setName(username+"name");
		user.setPassword(username);
		return user;
	}
}
