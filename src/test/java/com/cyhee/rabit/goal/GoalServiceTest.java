package com.cyhee.rabit.goal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyhee.rabit.cmm.AuthTestUtil;
import com.cyhee.rabit.cmm.CmmTestUtil;
import com.cyhee.rabit.cmm.factory.GoalFactory;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.goal.GoalService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@TestPropertySource(properties="spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect")
@Import({GoalService.class, BCryptPasswordEncoder.class})
public class GoalServiceTest {
	@Autowired
	private TestEntityManager entityManager;	
	@Autowired
	private GoalService goalService;
	
	User user1;
	User user2;
	User user3;
	User user4;
	Goal goal1;
	Goal goal2;
	
	@Before
	public void setup() {
		AuthTestUtil.setAdmin();

		user1 = new User().setEmail("email1@a").setUsername("username");		
		user2 = new User().setEmail("email2@a").setUsername("testuser2");
		user3 = new User().setEmail("email23@a").setUsername("testuser3");

		entityManager.persist(user1);
		entityManager.persist(user2);
		entityManager.persist(user3);

		goal1 = GoalFactory.base(user1, "content1");
		goal2 = GoalFactory.base(user2, "content2");

		entityManager.persist(goal1);
		entityManager.persist(goal2);
	}
	
	@Test
	public void createAndGet() {		
		Goal goal = GoalFactory.base(user1, "content1");
		goalService.addGoal(goal);
				
		assertThat(goalService.getGoal(goal.getId()))
			.isEqualTo(goal);
		assertThat(goalService.getGoalsByAuthor(user1))
			.containsExactlyInAnyOrder(goal, goal1);
		
		goal.setStatus(ContentStatus.FORBIDDEN);
		goal1.setStatus(ContentStatus.ACTIVE);		
		assertThat(goalService.getGoalsByAuthorStatusIn(user1, Arrays.asList(ContentStatus.FORBIDDEN),
			PageRequest.of(0, 1000)))
			.hasSize(1)
			.containsExactly(goal);		
		
		assertThatThrownBy(() -> {
			goalService.getGoal(1000L);
		})
			.isInstanceOf(NoSuchContentException.class)
			.hasMessage("No such goal with id '1000'.");
	}
	
	@Test
	public void update() {
		try {
			CmmTestUtil.updateWithAuthentication(goal1, long.class, goalService, "content", "startDate", "endDate", "status", "doUnit", "doTimes");
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}
	
	@Test
	public void delete() {
		try {
			CmmTestUtil.deleteWithAuthentication(goal1, long.class, goalService);
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}
	
	@Test
	public void search() {
		Pageable pageable = PageRequest.of(0, 10);
		assertThat(goalService.search("tent1", ContentStatus.all(), pageable))
			.hasSize(1)
			.contains(goal1);
	}
}
