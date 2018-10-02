package com.cyhee.rabit.goalgl;

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
	GoalLog gl1;
	GoalLog gl2;
	Comment comment1;
	Comment comment2;
		
	@Before
	public void setup() {
		user1 = new User().setEmail("email1@com").setUsername("user1");		
		user2 = new User().setEmail("email2@com").setUsername("user2");
		
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
		
		comment1 = new Comment().setAuthor(user1).setType(ContentType.GOALLOG).setContent("comment").setParentId(gl1.getId());
		comment2 = new Comment().setAuthor(user1).setType(ContentType.GOALLOG).setContent("comment").setParentId(gl2.getId());
		
		entityManger.persist(comment1);
		entityManger.persist(comment2);
	}
	
	@Test
	public void getComments() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Comment> comments = goalLogStoreService.getComments(gl1, pageable);
		
		assertThat(comments.getContent())
			.hasSize(1).contains(comment1);
		
		comments = goalLogStoreService.getComments(gl2, pageable);
		
		assertThat(comments.getContent())
			.hasSize(1).contains(comment2);
	}

	@Test
	public void deleteAndGet() {

		goalLogStoreService.deleteGoalLog(gl1.getId());

		assertThat(gl1)
				.extracting(GoalLog::getStatus)
				.containsExactly(ContentStatus.DELETED);

		assertThat(comment1)
				.extracting(Comment::getStatus)
				.containsExactly(ContentStatus.DELETED);
	}
}
