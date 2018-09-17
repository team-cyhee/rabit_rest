package com.cyhee.rabit.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

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
import org.springframework.test.context.junit4.SpringRunner;

import com.cyhee.rabit.dao.user.UserRepository;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.model.user.UserStatus;
import com.cyhee.rabit.service.user.BasicUserService;
import com.cyhee.rabit.service.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@Import({BasicUserService.class, BCryptPasswordEncoder.class})
public class UserServiceTest {
	@Autowired
	private TestEntityManager entityManger;
	@Autowired
	private UserRepository repository;	
	@Autowired
	private UserService userService;
	
	User user1;
	User user2;
	User user3;
	Date now;
	Date after;
	
	@Before
	public void setup() {
		now = new Date();
		user1 = new User().setEmail("email1@a").setPassword("password1@").setUsername("username");		
		user2 = new User().setEmail("email2@a").setPassword("password2@").setUsername("testuser2");
		user3 = new User().setEmail("email23@a").setPassword("password2@").setUsername("testuser3");
	}

	@Test
	public void createAndGet() throws InterruptedException {
		now = new Date();
		userService.addUser(user1);
		after = new Date(now.getTime() + 1000);
		
		Optional<User> userOpt = repository.findByEmail("email1@a");
		
		User user = userOpt.get();
		assertThat(user)
			.isEqualTo(user1)
			.extracting(User::getId, User::getCreateDate, User::getLastUpdated, User::getStatus)
				.doesNotContainNull()
				.contains(UserStatus.PENDING);
		assertThat(user)
			.extracting(User::getCreateDate, User::getLastUpdated)
			.allSatisfy(date -> {
                assertThat(now.compareTo((Date) date)).isNotPositive();
                assertThat(after.compareTo((Date) date)).isPositive();
              });
		assertThat(user.getPassword())
			.isNotEqualTo("password1");
	}
	
	
	@Test
	public void deleteAndGet() {
		Pageable pageable = PageRequest.of(0, 10);
		userService.addUser(user1);

		String email1 = "email1@a";
		User source = new User().setStatus(UserStatus.DELETED);
		Optional<User> userOpt = repository.findByEmail(email1);
		
		User user = userOpt.get();
		userService.deleteUser(user.getId(), pageable);
		
		userOpt = repository.findByEmail(email1);
		assertThat(userOpt.get())
				.extracting(User::getStatus)
				.containsExactly(source.getStatus());
	}
	
	@Test
	public void createAndGetAll() {
		userService.addUser(user1);
		userService.addUser(user2);
		userService.addUser(user3);
		
		Iterable<User> userList = repository.findAll();
		assertThat(userList)
			.hasSize(3);

		Pageable pageable = PageRequest.of(1, 2);
		Page<User> userPage = repository.findAll(pageable);
		assertThat(userPage)
			.hasSize(1)
			.containsExactly(user3);
	}
	
	@Test
	public void update() {
		userService.addUser(user1);
		userService.addUser(user2);
		
		entityManger.flush();
		entityManger.clear();
		
		user1.setName("updatedName");
		userService.updateUser(user1.getId(), user1);
		
		entityManger.flush();
		entityManger.clear();
		
		Optional<User> userOpt = repository.findByEmail("email1@a");		
		User user = userOpt.get();
		
		assertThat(user.getName())
			.isEqualTo("updatedName");
	}
}
