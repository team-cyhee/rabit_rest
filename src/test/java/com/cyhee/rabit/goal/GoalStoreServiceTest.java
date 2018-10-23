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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyhee.rabit.cmm.AuthTestUtil;
import com.cyhee.rabit.cmm.CmmTestUtil;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cmm.RadioStatus;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.service.comment.CommentStoreService;
import com.cyhee.rabit.service.goal.CompanionService;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goal.GoalStoreService;
import com.cyhee.rabit.service.page.GoalLogInfoService;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;
import com.cyhee.rabit.service.like.LikeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@TestPropertySource(properties="spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect")
@Import({GoalStoreService.class, GoalService.class, GoalLogService.class, GoalLogStoreService.class, CommentService.class, CommentStoreService.class, LikeService.class,
	GoalLogInfoService.class, CompanionService.class})
public class GoalStoreServiceTest {
	@Autowired
	private GoalStoreService goalStoreService;
	@Autowired
	private TestEntityManager entityManger;
	
	User user1;
	User user2;
	Goal goal1;
	Goal goal2;
	GoalLog goalLogInGoal1;
	GoalLog gl2;
	Comment commentInGoal1;
	Comment commentInGoal2;
	Comment commentInGoalLog;
	Like likeOnGoal;
	Like likeOnGoalLog;
	Like likeOnCommentInGoal;
	Like likeOnCommentInGoalLog;
		
	@Before
	public void setup() {
		AuthTestUtil.setAdmin();
		
		user1 = new User().setEmail("email1@com").setUsername("user1");		
		user2 = new User().setEmail("email2@com").setUsername("user2");
		
		goal1 = new Goal().setAuthor(user1).setContent("content1");
		goal2 = new Goal().setAuthor(user2).setContent("content2");
		
		goalLogInGoal1 = new GoalLog().setGoal(goal1).setContent("content1");
		gl2 = new GoalLog().setGoal(goal2).setContent("content2");
		
		entityManger.persist(user1);
		entityManger.persist(user2);
		entityManger.persist(goal1);
		entityManger.persist(goal2);
		entityManger.persist(goalLogInGoal1);
		entityManger.persist(gl2);
		
		commentInGoal1 = new Comment().setAuthor(user1).setType(ContentType.GOAL).setContent("comment").setParentId(goal1.getId());
		commentInGoal2 = new Comment().setAuthor(user1).setType(ContentType.GOAL).setContent("comment").setParentId(goal2.getId());
		commentInGoalLog = new Comment().setAuthor(user1).setType(ContentType.GOALLOG).setContent("comment").setParentId(goalLogInGoal1.getId());
		likeOnGoal = new Like().setAuthor(user2).setType(ContentType.GOAL).setParentId(goal1.getId());
		likeOnGoalLog = new Like().setAuthor(user2).setType(ContentType.GOALLOG).setParentId(goalLogInGoal1.getId());
		
		entityManger.persist(commentInGoal1);
		entityManger.persist(commentInGoal2);
		entityManger.persist(commentInGoalLog);

		likeOnCommentInGoal = new Like().setAuthor(user2).setType(ContentType.COMMENT).setParentId(commentInGoal1.getId());
		likeOnCommentInGoalLog = new Like().setAuthor(user2).setType(ContentType.COMMENT).setParentId(commentInGoalLog.getId()); 

		entityManger.persist(likeOnGoal);
		entityManger.persist(likeOnGoalLog);
		entityManger.persist(likeOnCommentInGoal);
		entityManger.persist(likeOnCommentInGoalLog);
	}
	
	@Test
	public void getGls() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<GoalLog> gls = goalStoreService.getGoalLogs(goal1, ContentStatus.all(), pageable);
		
		assertThat(gls.getContent())
			.hasSize(1).contains(goalLogInGoal1);
		
		gls = goalStoreService.getGoalLogs(goal2, ContentStatus.all(), pageable);
		
		assertThat(gls.getContent())
			.hasSize(1).contains(gl2);
	}
	
	@Test
	public void getComments() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Comment> comments = goalStoreService.getComments(goal1, pageable);
		System.out.println(commentInGoal1);
		assertThat(comments.getContent())
			.hasSize(1).contains(commentInGoal1);
		
		comments = goalStoreService.getComments(goal2, pageable);
		
		assertThat(comments.getContent())
			.hasSize(1).contains(commentInGoal2);
	}

	@Test
	public void getLikes() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Like> likes = goalStoreService.getLikes(goal1, pageable);

		assertThat(likes)
				.hasSize(1)
				.containsExactlyInAnyOrder(likeOnGoal);
	}
	
	@Test
	public void deleteGoal() {
		try {
			CmmTestUtil.deleteWithAuthentication(goal1, long.class, goalStoreService);
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}

		assertThat(goal1)
				.extracting(Goal::getStatus)
				.containsExactly(ContentStatus.DELETED);

		assertThat(goalLogInGoal1)
				.extracting(GoalLog::getStatus)
				.containsExactly(ContentStatus.DELETED);

		assertThat(commentInGoal1)
				.extracting(Comment::getStatus)
				.containsExactly(ContentStatus.DELETED);
		
		assertThat(likeOnGoal)
				.extracting(Like::getStatus)
				.containsExactly(RadioStatus.INACTIVE);
		
		assertThat(likeOnGoalLog)
				.extracting(Like::getStatus)
				.containsExactly(RadioStatus.INACTIVE);
		
		assertThat(likeOnCommentInGoal)
				.extracting(Like::getStatus)
				.containsExactly(RadioStatus.INACTIVE);

		assertThat(likeOnCommentInGoalLog)
				.extracting(Like::getStatus)
				.containsExactly(RadioStatus.INACTIVE);
	}
	
}
