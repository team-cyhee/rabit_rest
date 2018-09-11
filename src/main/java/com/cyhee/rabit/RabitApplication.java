package com.cyhee.rabit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.user.BasicUserService;
import com.cyhee.rabit.service.user.UserService;

@SpringBootApplication
@EnableAspectJAutoProxy
public class RabitApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RabitApplication.class, args);
		
		List<User> users = new ArrayList<>();
		users.add(userGenerator("user1"));
		users.add(userGenerator("user2"));
		
		User me = new User();
		me.setEmail("whgksdyd112@naver.com");
		me.setUsername("chy");
		me.setName("���ѿ�");
		me.setPassword(UUID.randomUUID().toString());
		users.add(me);

		UserService userService = (UserService)context.getBean(BasicUserService.class);
		
		for (User user : users) {			
			try {
				userService.getUserByUsername(user.getUsername());
			} catch (NoSuchContentException e) {
				userService.addUser(user);
			}
		}
		
		/*List<Goal> goals = new ArrayList<>();
		goals.add(new Goal().setAuthor(userService.getUserByUsername("chy")));
		
		GoalService goalService = (GoalService)context.getBean(BasicGoalService.class);
		for (Goal goal : goals) {			
			goalService.addGoal(goal);
		}
		
		List<Comment> comments = new ArrayList<>();
		comments.add(new Comment().setAuthor(userService.getUserByUsername("chy")).setParentId(goals.get(0).getId()).setType(ContentType.GOAL));
		
		CommentService commentService = (CommentService)context.getBean(BasicCommentService.class);
		for (Comment comment: comments) {
			commentService.addComment(comment);
		}*/
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