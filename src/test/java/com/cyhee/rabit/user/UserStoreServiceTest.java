package com.cyhee.rabit.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.cyhee.rabit.model.follow.Follow;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.user.UserStoreService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@TestPropertySource(properties="spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect")
@Import(UserStoreService.class)
public class UserStoreServiceTest {
	@Autowired
	UserStoreService userStoreService;
	@Autowired
	TestEntityManager entityManger;
	
	User user1;
	User user2;
	User user3;
	Follow follow1;
	Follow follow2;
	Goal goal1;
	Goal goal2;
	GoalLog log1;
	GoalLog log2;
		
	@Before
	public void setup() {
		user1 = new User().setEmail("email1@com").setPassword("password1@").setUsername("user1");		
		user2 = new User().setEmail("email2@com").setPassword("password2@").setUsername("user2");

		follow1 = new Follow().setFollower(user1).setFollowee(user2);
		follow2 = new Follow().setFollower(user1).setFollowee(user3);

		goal1 = new Goal().setAuthor(user1).setContent("content1");
		goal2 = new Goal().setAuthor(user2).setContent("content2");
		
		log1 = new GoalLog().setGoal(goal1).setContent("content1");
		log2 = new GoalLog().setGoal(goal2).setContent("content2");

		entityManger.persist(user1);
		entityManger.persist(user2);
		entityManger.persist(follow1);
		entityManger.persist(follow2);
		entityManger.persist(goal1);
		entityManger.persist(goal2);
		entityManger.persist(log1);
		entityManger.persist(log2);
	}

	//@Test
	public void getFollows() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Follow> follows = userStoreService.getFollowees(user1, pageable);

		assertThat(follows.getContent())
			.hasSize(2)
			.contains(follow1)
			.contains(follow2);
	}

	@Test
	public void getGoals() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Goal> goals = userStoreService.getGoals(user1, pageable);
		
		assertThat(goals.getContent())
			.hasSize(1).contains(goal1);
		
		goals = userStoreService.getGoals(user2, pageable);
		
		assertThat(goals.getContent())
			.hasSize(1).contains(goal2);
	}
	
	@Test
	public void getLogs() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<GoalLog> logs = userStoreService.getGoalLogs(user1, pageable);
		
		assertThat(logs.getContent())
			.hasSize(1).contains(log1);
		
		logs = userStoreService.getGoalLogs(user2, pageable);
		
		assertThat(logs.getContent())
			.hasSize(1).contains(log2);
	}
	
}
