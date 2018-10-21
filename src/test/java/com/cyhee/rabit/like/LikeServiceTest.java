package com.cyhee.rabit.like;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyhee.rabit.cmm.AuthTestUtil;
import com.cyhee.rabit.cmm.CmmTestUtil;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.like.LikeService;
import com.cyhee.rabit.service.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@TestPropertySource(properties="spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect")
@Import({UserService.class, BCryptPasswordEncoder.class, GoalService.class, GoalLogService.class, CommentService.class, LikeService.class})
public class LikeServiceTest {
	@Autowired
	private GoalService goalService;
	@Autowired
	private GoalLogService goalLogService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private TestEntityManager entityManger;

	User user1;
	User user2;
	Goal goal;
	GoalLog goalLog;
	Comment commentForGoal;
	Comment commentForGoalLog;
	Like likeForGoal;
	Like likeForGoalLog;
	Like likeForCommentInGoal;
	Like likeForCommentInGoalLog;


	@Before
	public void setup() {
		AuthTestUtil.setAdmin();
		
		user1 = new User().setEmail("user@email.com").setUsername("username");		
		user2 = new User().setEmail("email2@a").setUsername("testuser2");

		goal = new Goal().setAuthor(user1).setContent("goal");
		goalLog = new GoalLog().setGoal(goal).setContent("goalLogForGoal");

		entityManger.persist(user1);
		entityManger.persist(user2);
		entityManger.persist(goal);
		entityManger.persist(goalLog);

		commentForGoal = new Comment().setType(ContentType.GOAL).setParentId(goal.getId()).setAuthor(user2).setContent("commentForGoal");
		commentForGoalLog = new Comment().setType(ContentType.GOALLOG).setParentId(goalLog.getId()).setAuthor(user2).setContent("commentForGoalLog");

		entityManger.persist(commentForGoal);
		entityManger.persist(commentForGoalLog);

		likeForGoal = new Like().setAuthor(user2).setType(ContentType.GOAL).setParentId(goal.getId());
		likeForGoalLog = new Like().setAuthor(user2).setType(ContentType.GOALLOG).setParentId(goalLog.getId());
		likeForCommentInGoal = new Like().setAuthor(user1).setType(ContentType.COMMENT).setParentId(commentForGoal.getId());
		likeForCommentInGoalLog = new Like().setAuthor(user1).setType(ContentType.COMMENT).setParentId(commentForGoalLog.getId());

		entityManger.persist(likeForGoal);
		entityManger.persist(likeForGoalLog);
		entityManger.persist(likeForCommentInGoal);
		entityManger.persist(likeForCommentInGoalLog);

		goalService.addGoal(goal);
		goalLogService.addGoalLog(goalLog);
		commentService.addComment(commentForGoal);
		commentService.addComment(commentForGoalLog);
		likeService.addLike(likeForGoal);
		likeService.addLike(likeForGoalLog);
		likeService.addLike(likeForCommentInGoal);
		likeService.addLike(likeForCommentInGoalLog);
	}

	@Test
	public void createAndGet() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Like> likes = likeService.getLikes(ContentType.GOAL, goal.getId(), pageable);
		
		assertThat(likes)
			.hasSize(1)
			.containsExactlyInAnyOrder(likeForGoal);

		likes = likeService.getLikes(ContentType.GOALLOG, goalLog.getId(), pageable);

		assertThat(likes)
				.hasSize(1)
				.containsExactlyInAnyOrder(likeForGoalLog);

		likes = likeService.getLikes(ContentType.COMMENT, commentForGoal.getId(), pageable);

		assertThat(likes)
				.hasSize(1)
				.containsExactlyInAnyOrder(likeForCommentInGoal);
	}

	@Test
	public void update() {
		try {
			CmmTestUtil.updateWithAuthentication(likeForGoal, long.class, likeService, "status");
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}		
	}

	@Test
	public void deleteAndGet() {
		try {
			CmmTestUtil.deleteWithAuthentication(likeForGoal, long.class, likeService);
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}

}
