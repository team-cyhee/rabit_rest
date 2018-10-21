package com.cyhee.rabit.follow;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyhee.rabit.cmm.AuthTestUtil;
import com.cyhee.rabit.cmm.CmmTestUtil;
import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.follow.FollowService;
import com.cyhee.rabit.service.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@TestPropertySource(properties="spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect")
@Import({UserService.class, BCryptPasswordEncoder.class, UserService.class, FollowService.class})
public class FollowServiceTest {
    @Autowired
    private FollowService followService;    

	@Autowired
	private TestEntityManager entityManger;

    User user1;
    User user2;
    User user3;
    Follow follow1;

    @Before
    public void setup() {
    	AuthTestUtil.setAdmin();
    	
        user1 = new User().setEmail("email1@com").setUsername("user1");
        user2 = new User().setEmail("email2@com").setUsername("user2");
        user3 = new User().setEmail("email3@com").setUsername("user3");
        
    	entityManger.persist(user1);
    	entityManger.persist(user2);
    	entityManger.persist(user3);

        follow1 = new Follow().setFollower(user1).setFollowee(user2);
    }

    @Test
    public void createAndGet() {
        followService.addFollow(follow1);

        Follow follow = followService.getFollow(follow1.getId());

        assertThat(follow)
            .isEqualTo(follow1);
    }

    @Test
    public void update() {
        followService.addFollow(follow1);
		try {
			CmmTestUtil.updateWithAuthentication(follow1, long.class, followService, "status");
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}
    }

    @Test
    public void deleteAndGet() {
        followService.addFollow(follow1);
		try {
			CmmTestUtil.deleteWithAuthentication(follow1, long.class, followService);
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}
    }
}
