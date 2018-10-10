package com.cyhee.rabit.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

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

import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.model.user.UserStatus;
import com.cyhee.rabit.service.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@TestPropertySource(properties="spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect")
@Import({UserService.class, BCryptPasswordEncoder.class})
public class UserServiceTest {
	@Autowired
	private TestEntityManager entityManger;	
	@Autowired
	private UserService userService;
	
	User user1;
	User user2;
	User user3;
	User user4;
	Date now;
	Date after;
	
	@Before
	public void setup() {
		now = new Date();
		user1 = new User().setEmail("email1@a").setUsername("username");		
		user2 = new User().setEmail("email2@a").setUsername("testuser2");
		user3 = new User().setEmail("email23@a").setUsername("testuser3");

		entityManger.persist(user1);
		entityManger.persist(user2);
		entityManger.persist(user3);
	}

	@Test
	public void get() throws InterruptedException {
		String email = "thisEmail@com";
		User base = new User().setEmail(email).setUsername("testuser4");
		now = new Date();
		entityManger.persist(base);
		after = new Date(now.getTime() + 1000);
		
		User user = userService.getUserByEmail(email);
		
		assertThat(user)
			.isEqualTo(base)
			.extracting(User::getId, User::getCreateDate, User::getLastUpdated, User::getStatus)
				.doesNotContainNull()
				.contains(UserStatus.PENDING);
		assertThat(user)
			.extracting(User::getCreateDate, User::getLastUpdated)
			.allSatisfy(date -> {
                assertThat(now.compareTo((Date) date)).isNotPositive();
                assertThat(after.compareTo((Date) date)).isPositive();
              });
	}
	
	@Test
	public void getPage() {
		
		Pageable pageable = PageRequest.of(1, 2);

		Page<User> userPage = userService.getUsers(pageable);
		assertThat(userPage)
			.hasSize(1)
			.containsExactly(user3);
	}
	
	@Test
	public void update() {
		user1.setName("updatedName");
		userService.updateUser(user1.getId(), user1);
				
		User user = userService.getUserByUsername(user1.getUsername());
		
		assertThat(user.getName())
			.isEqualTo("updatedName");
	}
	
	@Test
	public void delete() {
		userService.deleteUser(user1.getId());
		
		assertThat(user1.getStatus())
			.isEqualTo(UserStatus.DELETED);
	}
}
