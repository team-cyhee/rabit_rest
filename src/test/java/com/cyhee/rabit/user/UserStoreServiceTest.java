package com.cyhee.rabit.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyhee.rabit.goal.model.Goal;
import com.cyhee.rabit.goallog.model.GoalLog;
import com.cyhee.rabit.user.model.User;
import com.cyhee.rabit.user.store.service.UserStoreService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@Import(UserStoreService.class)
public class UserStoreServiceTest {
	@Autowired
	UserStoreService userStoreService;
	
	User user1;
	User user2;
	Goal goal1;
	Goal goal2;
	Goal goal3;
	Goal goal4;
		
	@Before
	public void setup() {
		user1 = new User().setEmail("email1@com").setPassword("password1@").setUsername("user1");		
		user2 = new User().setEmail("email2@com").setPassword("password2@").setUsername("user2");
		
		goal1 = new Goal().setAuthor(user1).setContent("content1");
		goal2 = new Goal().setAuthor(user2).setContent("content2");
	}
	
	@Test
	public void get() {
		
	}
}
