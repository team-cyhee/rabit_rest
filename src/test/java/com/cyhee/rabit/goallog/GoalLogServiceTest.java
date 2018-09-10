package com.cyhee.rabit.goallog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.service.goal.BasicGoalService;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.service.goallog.BasicGoalLogService;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.user.BasicUserService;
import com.cyhee.rabit.service.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@Import({BasicUserService.class, BCryptPasswordEncoder.class, BasicGoalService.class, BasicGoalLogService.class})
public class GoalLogServiceTest {
	@Autowired
	private UserService userService;
	@Autowired
	private GoalService goalService;
	@Autowired
	private GoalLogService goalLogService;
	
	User user1;
	User user2;
	Goal goal1;
	Goal goal2;
	GoalLog log1;
	GoalLog log2;
	GoalLog log3;
	GoalLog log4;
		
	@Before
	public void setup() {
		user1 = new User().setEmail("email1@com").setPassword("password1@").setUsername("user1");		
		user2 = new User().setEmail("email2@com").setPassword("password2@").setUsername("user2");
		
		goal1 = new Goal().setAuthor(user1).setContent("content1");
		goal2 = new Goal().setAuthor(user2).setContent("content2");
		
		log1 = new GoalLog().setGoal(goal1).setContent("content1");
		log2 = new GoalLog().setGoal(goal2).setContent("content2");
		log3 = new GoalLog().setGoal(goal1).setContent("content3");
		log4 = new GoalLog().setGoal(goal2).setContent("content4");
	}

	@Test
	public void createAndGet() {
		userService.addUser(user1);
		goalService.addGoal(goal1);
		goalLogService.addGoalLog(log1);
		
		GoalLog log = goalLogService.getGoalLog(log1.getId());
		
		assertThat(log)
			.isEqualTo(log1);
	}
	
	
	@Test
	public void deleteAndGet() {
		userService.addUser(user1);
		goalService.addGoal(goal1);
		goalLogService.addGoalLog(log1);				
		goalLogService.deleteGoalLog(log1.getId());		
		
		assertThatThrownBy(() -> {
			goalLogService.getGoalLog(log1.getId());
		});
	}
	
	@Test
	public void createAndGetAll() {
		userService.addUser(user1);
		userService.addUser(user2);
		goalService.addGoal(goal1);
		goalService.addGoal(goal2);
		goalLogService.addGoalLog(log1);
		goalLogService.addGoalLog(log2);
		goalLogService.addGoalLog(log3);
		goalLogService.addGoalLog(log4);

		Pageable pageable = PageRequest.of(1, 2);
		Page<GoalLog> page = goalLogService.getGoalLogs(pageable);
		assertThat(page)
			.hasSize(2)
			.containsExactlyInAnyOrder(log3, log4);
	}
	
	@Test
	public void update() {
		userService.addUser(user1);
		goalService.addGoal(goal1);
		goalLogService.addGoalLog(log1);
		
		GoalLog form = new GoalLog().setContent("new Content");
		
		goalLogService.updateGoalLog(log1.getId(), form);
		
		assertThat(log1)
			.extracting(GoalLog::getContent)
			.containsExactly(form.getContent());
	}
}
