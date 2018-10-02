package com.cyhee.rabit.goallog;

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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@TestPropertySource(properties="spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect")
@Import({GoalLogStoreService.class, CommentService.class, GoalLogService.class})
public class GoalLogStoreServiceTest {
	@Autowired
	private GoalLogStoreService goalLogStoreService;
	@Autowired
	private TestEntityManager entityManger;
	
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
		user1 = new User().setEmail("email1@com").setUsername("user1");		
		user2 = new User().setEmail("email2@com").setUsername("user2");
		
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
		
		comment1 = new Comment().setAuthor(user1).setType(ContentType.GOALLOG).setContent("comment").setParentId(log1.getId());
		comment2 = new Comment().setAuthor(user1).setType(ContentType.GOALLOG).setContent("comment").setParentId(log2.getId());
		
		entityManger.persist(comment1);
		entityManger.persist(comment2);
	}
	
	@Test
	public void getComments() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Comment> comments = goalLogStoreService.getComments(log1, pageable);
		
		assertThat(comments.getContent())
			.hasSize(1).contains(comment1);
		
		comments = goalLogStoreService.getComments(log2, pageable);
		
		assertThat(comments.getContent())
			.hasSize(1).contains(comment2);
	}

	@Test
	public void deleteAndGet() {
		GoalLog glSource = new GoalLog().setStatus(ContentStatus.DELETED);
		Comment cmtSource = new Comment().setStatus(ContentStatus.DELETED);

		goalLogStoreService.deleteGoalLog(log1.getId());

		assertThat(log1)
				.extracting(GoalLog::getStatus)
				.containsExactly(glSource.getStatus());

		assertThat(comment1)
				.extracting(Comment::getStatus)
				.containsExactly(cmtSource.getStatus());
	}
}
