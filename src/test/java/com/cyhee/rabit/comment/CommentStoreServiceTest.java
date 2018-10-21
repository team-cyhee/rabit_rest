package com.cyhee.rabit.comment;

import com.cyhee.rabit.cmm.AuthTestUtil;
import com.cyhee.rabit.cmm.CmmTestUtil;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cmm.RadioStatus;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.service.comment.CommentStoreService;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;
import com.cyhee.rabit.service.like.LikeService;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@TestPropertySource(properties="spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect")
@Import({GoalLogStoreService.class, CommentService.class, GoalLogService.class, CommentStoreService.class, LikeService.class})
public class CommentStoreServiceTest {
	@Autowired
	private CommentStoreService commentStoreService;
	@Autowired
	private TestEntityManager entityManger;
	
	User user1;
	User user2;
	Goal goal1;
	Comment comment1;
	Like like1;
	Like like2;
		
	@Before
	public void setup() {
    	AuthTestUtil.setAdmin();
    	
		user1 = new User().setEmail("email1@com").setUsername("user1");		
		user2 = new User().setEmail("email2@com").setUsername("user2");
		
		goal1 = new Goal().setAuthor(user1).setContent("content1");
		
		entityManger.persist(user1);
		entityManger.persist(user2);
		entityManger.persist(goal1);
		
		comment1 = new Comment().setAuthor(user2).setType(ContentType.GOAL).setContent("comment").setParentId(goal1.getId());

		entityManger.persist(comment1);

		like1 = new Like().setAuthor(user1).setType(ContentType.COMMENT).setParentId(comment1.getId());
		like2 = new Like().setAuthor(user1).setType(ContentType.COMMENT).setParentId(comment1.getId());

		entityManger.persist(like1);
		entityManger.persist(like2);
	}

	@Test
	public void getLikes() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Like> likes = commentStoreService.getLikes(comment1, pageable);

		assertThat(likes)
				.hasSize(2)
				.containsExactlyInAnyOrder(like1, like2);
	}

	@Test
	public void deleteAndGet() {
		try {
			CmmTestUtil.deleteWithAuthentication(comment1, long.class, commentStoreService);
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}

		assertThat(like1)
				.extracting(Like::getStatus)
				.containsExactly(RadioStatus.INACTIVE);

		assertThat(like2)
				.extracting(Like::getStatus)
				.containsExactly(RadioStatus.INACTIVE);
	}
}
