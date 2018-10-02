package com.cyhee.rabit.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goal.GoalStoreService;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;
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

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.model.user.UserStatus;
import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.service.follow.FollowService;
import com.cyhee.rabit.service.user.UserService;
import com.cyhee.rabit.service.user.UserStoreService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@TestPropertySource(properties="spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect")
@Import({UserStoreService.class, UserService.class, GoalService.class, GoalLogService.class, GoalStoreService.class, GoalLogStoreService.class, FollowService.class, CommentService.class})
public class UserStoreServiceTest {
	@Autowired
	private UserStoreService userStoreService;
	@Autowired
	private TestEntityManager entityManger;
	
	User user1;
	User user2;
	User user3;
	Follow follow1;
	Follow follow2;
	Goal goal1;
	Goal goal2;
	GoalLog gl1;
	GoalLog gl2;
	Comment commentInGoal1ByUser1;
	Comment commentInGoalLog1ByUser2;
	Comment commentInGoal2ByUser1;
		
	@Before
	public void setup() {
		user1 = new User().setEmail("email1@com").setUsername("user1");		
		user2 = new User().setEmail("email2@com").setUsername("user2");

		follow1 = new Follow().setFollower(user1).setFollowee(user2);
		follow2 = new Follow().setFollower(user1).setFollowee(user3);

		goal1 = new Goal().setAuthor(user1).setContent("content1");
		goal2 = new Goal().setAuthor(user2).setContent("content2");
		
		gl1 = new GoalLog().setGoal(goal1).setContent("content1");
		gl2 = new GoalLog().setGoal(goal2).setContent("content2");

		entityManger.persist(user1);
		entityManger.persist(user2);
		entityManger.persist(follow1);
		entityManger.persist(follow2);
		entityManger.persist(goal1);
		entityManger.persist(goal2);
		entityManger.persist(gl1);
		entityManger.persist(gl2);

		commentInGoal1ByUser1 = new Comment().setAuthor(user1).setType(ContentType.GOAL).setContent("commentInGoal1ByUser1").setParentId(goal1.getId());
		commentInGoalLog1ByUser2 = new Comment().setAuthor(user2).setType(ContentType.GOALLOG).setContent("commentInGoalLog1ByUser2").setParentId(gl1.getId());
		commentInGoal2ByUser1 = new Comment().setAuthor(user1).setType(ContentType.GOAL).setContent("commentInGoal2ByUser1").setParentId(goal2.getId());

		entityManger.persist(commentInGoal1ByUser1);
		entityManger.persist(commentInGoalLog1ByUser2);
		entityManger.persist(commentInGoal2ByUser1);
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
		Page<GoalLog> gls = userStoreService.getGoalLogs(user1, pageable);
		
		assertThat(gls.getContent())
			.hasSize(1).contains(gl1);
		
		gls = userStoreService.getGoalLogs(user2, pageable);
		
		assertThat(gls.getContent())
			.hasSize(1).contains(gl2);
	}

	@Test
	public void deleteAndGet() {

		userStoreService.deleteUser(user1.getId());

		assertThat(user1)
				.extracting(User::getStatus)
				.containsExactly(UserStatus.DELETED);

		assertThat(goal1)
				.extracting(Goal::getStatus)
				.containsExactly(ContentStatus.DELETED);

		assertThat(goal2)
				.extracting(Goal::getStatus)
				.containsExactly(ContentStatus.ACTIVE);

		assertThat(gl1)
				.extracting(GoalLog::getStatus)
				.containsExactly(ContentStatus.DELETED);

		// TODO delete comment
		assertThat(commentInGoal1ByUser1)
				.extracting(Comment::getStatus)
				.containsExactly(ContentStatus.DELETED);

		assertThat(commentInGoalLog1ByUser2)
				.extracting(Comment::getStatus)
				.containsExactly(ContentStatus.DELETED);

		assertThat(commentInGoal2ByUser1)
				.extracting(Comment::getStatus)
				.containsExactly(ContentStatus.ACTIVE);
	}
	
}
