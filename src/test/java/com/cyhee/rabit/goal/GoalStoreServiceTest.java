package com.cyhee.rabit.goal;

import static org.assertj.core.api.Assertions.assertThat;

import com.cyhee.rabit.model.cmm.ContentStatus;
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

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.service.user.UserService;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.service.goal.GoalStoreService;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.user.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@TestPropertySource(properties="spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect")
@Import({GoalStoreService.class, CommentService.class})
public class GoalStoreServiceTest {
	@Autowired
	private UserService userService;
	@Autowired
	private GoalService goalService;
	@Autowired
	private GoalLogService goalLogService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private GoalStoreService goalStoreService;
	@Autowired
	private TestEntityManager entityManger;
	
	User user1;
	User user2;
	Goal goal1;
	Goal goal2;
	GoalLog gl1;
	GoalLog gl2;
	Comment comment1;
	Comment comment2;
		
	@Before
	public void setup() {
		user1 = new User().setEmail("email1@com").setPassword("password1@").setUsername("user1");		
		user2 = new User().setEmail("email2@com").setPassword("password2@").setUsername("user2");
		
		goal1 = new Goal().setAuthor(user1).setContent("content1");
		goal2 = new Goal().setAuthor(user2).setContent("content2");
		
		gl1 = new GoalLog().setGoal(goal1).setContent("content1");
		gl2 = new GoalLog().setGoal(goal2).setContent("content2");
		
		entityManger.persist(user1);
		entityManger.persist(user2);
		entityManger.persist(goal1);
		entityManger.persist(goal2);
		entityManger.persist(gl1);
		entityManger.persist(gl2);
		
		comment1 = new Comment().setAuthor(user1).setType(ContentType.GOAL).setContent("comment").setParentId(goal1.getId());
		comment2 = new Comment().setAuthor(user1).setType(ContentType.GOAL).setContent("comment").setParentId(goal2.getId());
		
		entityManger.persist(comment1);
		entityManger.persist(comment2);
	}
	
	@Test
	public void getGls() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<GoalLog> gls = goalStoreService.getGoalLogs(goal1, pageable);
		
		assertThat(gls.getContent())
			.hasSize(1).contains(gl1);
		
		gls = goalStoreService.getGoalLogs(goal2, pageable);
		
		assertThat(gls.getContent())
			.hasSize(1).contains(gl2);
	}
	
	@Test
	public void getComments() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Comment> comments = goalStoreService.getComments(goal1, pageable);
		System.out.println(comment1);
		assertThat(comments.getContent())
			.hasSize(1).contains(comment1);
		
		comments = goalStoreService.getComments(goal2, pageable);
		
		assertThat(comments.getContent())
			.hasSize(1).contains(comment2);
	}

	@Test
	public void deleteGoal() {
		Goal gSource = new Goal().setStatus(ContentStatus.DELETED);
		GoalLog glSource = new GoalLog().setStatus(ContentStatus.DELETED);
		Comment cmtSource = new Comment().setStatus(ContentStatus.DELETED);

		userService.addUser(user1);
		goalService.addGoal(goal1);
		goalLogService.addGoalLog(gl1);
		commentService.addComment(comment1);

		goalStoreService.deleteGoal(gl1.getId());

		assertThat(goal1)
				.extracting(Goal::getStatus)
				.containsExactly(gSource.getStatus());

		assertThat(gl1)
				.extracting(GoalLog::getStatus)
				.containsExactly(glSource.getStatus());

		assertThat(comment1)
				.extracting(Comment::getStatus)
				.containsExactly(cmtSource.getStatus());
	}
	
}
