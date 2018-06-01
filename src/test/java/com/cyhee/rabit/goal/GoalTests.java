package com.cyhee.rabit.goal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyhee.rabit.goal.dao.GoalRepository;
import com.cyhee.rabit.goal.model.Goal;
import com.cyhee.rabit.goal.service.BasicGoalService;
import com.cyhee.rabit.goal.service.GoalService;
import com.cyhee.rabit.user.dao.UserRepository;
import com.cyhee.rabit.user.model.User;
import com.cyhee.rabit.user.service.BasicUserService;
import com.cyhee.rabit.user.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({BasicUserService.class, BCryptPasswordEncoder.class, BasicGoalService.class})
public class GoalTests {
	@Autowired
	private TestEntityManager entityManger;
	@Autowired
	private UserRepository userRepository;	
	@Autowired
	private UserService userService;
	@Autowired
	private GoalRepository goalRepository;
	@Autowired
	private GoalService goalService;
	
	User user1;
	User user2;
	Date now;
	Date after;
	Goal goal1;
	Goal goal2;
	Goal goal3;
	Goal goal4;
		
	@Before
	public void setup() {
		now = new Date();
		user1 = new User("email1","password1","user1","name1","010-1234-1234", now);
		user2 = new User("email2","password2","user2","name2","010-1234-1234", now);
		
		goal1 = new Goal(user1, null, "content1", now, now);
		goal2 = new Goal(user2, goal1, "content2", now, now);
		goal3 = new Goal(user2, null, "content3", now, now);
		goal4 = new Goal(user1, null, "content4", now, now);
	}

	@Test
	public void createAndGet() throws InterruptedException {
		now = new Date();
		userService.addUser(user1);
		goalService.addGoal(goal1);
		after = new Date(now.getTime() + 1000);

		entityManger.flush();
		entityManger.clear();
		
		Optional<Goal> goalOpt = goalRepository.findById(goal1.getId());
		
		Goal goal = goalOpt.get();
				
		assertThat(goal)
			.extracting(Goal::getId, Goal::getContent)
			.doesNotContainNull()
			.contains(goal1.getContent());
		assertThat(goal.getAuthor().getId())
			.isEqualTo(user1.getId());
		assertThat(goal)
			.extracting(Goal::getCreateDate, Goal::getLastUpdated)
			.allSatisfy(date -> {
                assertThat(now.compareTo((Date) date)).isLessThan(0);
                assertThat(after.compareTo((Date) date)).isGreaterThan(0);
              });
	}
	
	
	@Test
	public void deleteAndGet() {
		userService.addUser(user1);
		goalService.addGoal(goal1);
		
		userRepository.findByEmail("email1");		
		goalService.deleteGoal(goal1.getId());
		Optional<Goal> goalOpt = goalRepository.findById(goal1.getId());
		assertThat(goalOpt.isPresent())
			.isEqualTo(false);
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void createAndGetAll() {
		userService.addUser(user1);
		userService.addUser(user2);
		goalService.addGoal(goal1);
		goalService.addGoal(goal2);
		goalService.addGoal(goal3);
		
		Iterable<Goal> goalList = goalRepository.findAll();
		assertThat(goalList)
			.hasSize(3);

		Pageable pageable = new PageRequest(1, 2);
		Page<Goal> goalPage = goalRepository.findAll(pageable);
		assertThat(goalPage)
			.hasSize(1)
			.containsExactly(goal3);
	}
	
	@Test
	public void update() {
		userService.addUser(user1);
		userService.addUser(user2);
		goalService.addGoal(goal1);
		
		
		now = new Date();
		goalService.updateGoal(goal1.getId(), goal4);
		after = new Date(now.getTime() + 1000);

		entityManger.flush();
		entityManger.clear();
		
		Goal goal = goalService.getGoal(goal1.getId());
		
		assertThat(goal)
			.extracting(Goal::getId, Goal::getContent)
			.containsExactly(goal1.getId(), goal4.getContent());
		assertThat(goal.getAuthor().getId())
			.isEqualTo(user1.getId());
	}
}
