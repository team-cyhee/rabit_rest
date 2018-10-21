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
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.goal.CompanionService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@TestPropertySource(properties="spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect")
@Import({CompanionService.class})
public class CompanionServiceTest {
	@Autowired
	private CompanionService companionService;
	@Autowired
	private TestEntityManager entityManger;
	
	User user1;
	User user2;
	User user3;
	User user4;
	Goal goal1;
	Goal goal2;
	Goal goal3;
	Goal goal4;
	Goal goal5;
		
	@Before
	public void setup() {
		AuthTestUtil.setAdmin();
		
		user1 = new User().setEmail("email1@com").setUsername("user1");		
		user2 = new User().setEmail("email2@com").setUsername("user2");
		user3 = new User().setEmail("email3@com").setUsername("user3");
		user4 = new User().setEmail("email4@com").setUsername("user4");
		
		goal1 = new Goal().setAuthor(user1).setContent("content1");
		goal2 = new Goal().setAuthor(user2).setContent("content2").setParent(goal1);
		goal3 = new Goal().setAuthor(user3).setContent("content3").setParent(goal1);
		goal4 = new Goal().setAuthor(user3).setContent("content4");
		goal5 = new Goal().setAuthor(user4).setContent("content5");
		
		entityManger.persist(user1);
		entityManger.persist(user2);
		entityManger.persist(user3);
		entityManger.persist(user4);
		
		entityManger.persist(goal1);
		entityManger.persist(goal2);
		entityManger.persist(goal3);
		entityManger.persist(goal4);
		entityManger.persist(goal5);
	}
	
	@Test
	public void getCompanions() {
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<User> fromParent = companionService.getCompanions(goal1, pageable);
		assertThat(fromParent)
			.hasSize(2).contains(user2, user3);
		
		Page<User> fromChild = companionService.getCompanions(goal2, pageable);
		assertThat(fromChild)
			.hasSize(2).contains(user1, user3);
		
		Page<User> mustBeEmpty = companionService.getCompanions(goal4, pageable);
		assertThat(mustBeEmpty)
			.hasSize(0);
	}
	
	@Test
	public void getCompanionGoals() {
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<Goal> fromParent = companionService.getCompanionGoals(goal1, pageable);
		assertThat(fromParent)
			.hasSize(2).contains(goal2, goal3);
		
		Page<Goal> fromChild = companionService.getCompanionGoals(goal2, pageable);
		assertThat(fromChild)
			.hasSize(2).contains(goal1, goal3);
		
		Page<Goal> mustBeEmpty = companionService.getCompanionGoals(goal4, pageable);
		assertThat(mustBeEmpty)
			.hasSize(0);
	}
}
