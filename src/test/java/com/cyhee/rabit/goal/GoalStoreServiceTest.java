package com.cyhee.rabit.goal;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.test.context.junit4.SpringRunner;

import com.cyhee.rabit.cmm.model.ContentType;
import com.cyhee.rabit.comment.model.Comment;
import com.cyhee.rabit.comment.service.BasicCommentService;
import com.cyhee.rabit.goal.model.Goal;
import com.cyhee.rabit.goal.store.service.GoalStoreService;
import com.cyhee.rabit.goallog.model.GoalLog;
import com.cyhee.rabit.user.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@Import({GoalStoreService.class, BasicCommentService.class})
public class GoalStoreServiceTest {
	@Autowired
	GoalStoreService goalStoreService;
	@Autowired
	TestEntityManager entityManger;
	
	User user1;
	User user2;
	Goal goal1;
	Goal goal2;
	GoalLog log1;
	GoalLog log2;
	Comment comment1;
	Comment comment2;
		
	@Before
	public void setup() {
		user1 = new User().setEmail("email1@com").setPassword("password1@").setUsername("user1");		
		user2 = new User().setEmail("email2@com").setPassword("password2@").setUsername("user2");
		
		goal1 = new Goal().setAuthor(user1).setContent("content1");
		goal2 = new Goal().setAuthor(user2).setContent("content2");
		
		log1 = new GoalLog().setGoal(goal1).setContent("content1");
		log2 = new GoalLog().setGoal(goal2).setContent("content2");
		
		entityManger.persist(user1);
		entityManger.persist(user2);
		entityManger.persist(goal1);
		entityManger.persist(goal2);
		entityManger.persist(log1);
		entityManger.persist(log2);
		
		comment1 = new Comment().setAuthor(user1).setType(ContentType.GOAL).setContent("comment").setParentId(goal1.getId());
		comment2 = new Comment().setAuthor(user1).setType(ContentType.GOAL).setContent("comment").setParentId(goal2.getId());
		
		entityManger.persist(comment1);
		entityManger.persist(comment2);
	}
	
	@Test
	public void getLogs() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<GoalLog> logs = goalStoreService.getGoalLogs(goal1, pageable);
		
		assertThat(logs.getContent())
			.hasSize(1).contains(log1);
		
		logs = goalStoreService.getGoalLogs(goal2, pageable);
		
		assertThat(logs.getContent())
			.hasSize(1).contains(log2);
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
	
}
