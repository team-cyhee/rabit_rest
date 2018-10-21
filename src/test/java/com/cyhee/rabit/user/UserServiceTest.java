package com.cyhee.rabit.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
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

import com.cyhee.rabit.cmm.AuthTestUtil;
import com.cyhee.rabit.cmm.CmmTestUtil;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
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
		AuthTestUtil.setAdmin();
		
		now = new Date();
		user1 = new User().setEmail("email1@a").setUsername("username");		
		user2 = new User().setEmail("email2@a").setUsername("testuser2");
		user3 = new User().setEmail("email23@a").setUsername("testuser3");

		entityManger.persist(user1);
		entityManger.persist(user2);
		entityManger.persist(user3);
	}
	
	@Test
	public void get() {		
		assertThat(userService.getUser(user1.getId()))
			.isEqualTo(user1);
		assertThat(userService.getUserByUsername(user2.getUsername()))
			.isEqualTo(user2);
		assertThat(userService.getUserByEmail(user3.getEmail()))
			.isEqualTo(user3);
		
		
		assertThatThrownBy(() -> {
			userService.getUser(1000L);
		})
			.isInstanceOf(NoSuchContentException.class)
			.hasMessage("No such user with id '1000'.");
					
		assertThatThrownBy(() -> {
			userService.getUserByUsername("no_username");
		})
			.isInstanceOf(NoSuchContentException.class)
			.hasMessage("No such user with username 'no_username'.");
		
		assertThatThrownBy(() -> {
			userService.getUserByEmail("no_email");
		})
			.isInstanceOf(NoSuchContentException.class)
			.hasMessage("No such user with email 'no_email'.");
	}
	
	@Test
	public void getList() {
		
		Pageable pageable = PageRequest.of(1, 2);

		Page<User> userPage = userService.getUsers(pageable);
		assertThat(userPage)
			.hasSize(1)
			.containsExactly(user3);
		
		
		user1.setStatus(UserStatus.ACTIVE);
		user2.setStatus(UserStatus.FORBIDDEN);
		user3.setStatus(UserStatus.PENDING);
		
		userPage = userService.getUsersByStatusIn(Arrays.asList(UserStatus.FORBIDDEN, UserStatus.ACTIVE), PageRequest.of(0, 100));
		assertThat(userPage)
			.hasSize(2)
			.containsAll(Arrays.asList(user1, user2));
	}
	
	@Test
	public void update() {
		try {
			CmmTestUtil.updateWithAuthentication(user1, Long.class, userService, "name", "phone", "status", "birth");
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}
	
	@Test
	public void delete() {
		try {
			CmmTestUtil.deleteWithAuthentication(user1, Long.class, userService);
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}
}
