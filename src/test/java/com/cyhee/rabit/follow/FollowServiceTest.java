package com.cyhee.rabit.follow;

import static org.assertj.core.api.Assertions.assertThat;

import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.follow.FollowStatus;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.follow.FollowService;
import com.cyhee.rabit.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@TestPropertySource(properties="spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect")
@Import({UserService.class, BCryptPasswordEncoder.class, UserService.class, FollowService.class})
public class FollowServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    User user1;
    User user2;
    User user3;
    Follow follow1;

    @Before
    public void setup() {
        user1 = new User().setEmail("email1@com").setPassword("password1@").setUsername("user1");
        user2 = new User().setEmail("email2@com").setPassword("password2@").setUsername("user2");
        user2 = new User().setEmail("email3@com").setPassword("password3@").setUsername("user3");

        follow1 = new Follow().setFollower(user1).setFollowee(user2);
    }

    @Test
    public void createAndGet() {
        userService.addUser(user1);
        userService.addUser(user2);
        followService.addFollow(follow1);

        Follow follow = followService.getFollow(follow1.getId());

        assertThat(follow)
            .isEqualTo(follow1);
    }

    @Test
    public void update() {
        userService.addUser(user1);
        userService.addUser(user2);
        followService.addFollow(follow1);

        Follow form = new Follow().setFollowee(user3);

        followService.updateFollow(follow1.getId(), form);

        assertThat(follow1)
            .extracting(Follow::getFollowee)
            .containsExactly(form.getFollowee());
    }

    @Test
    public void deleteAndGet() {
        Follow source = new Follow().setStatus(FollowStatus.INACTIVE);

        userService.addUser(user1);
        userService.addUser(user2);
        followService.addFollow(follow1);

        followService.deleteFollow(follow1.getId());

        assertThat(follow1)
            .extracting(Follow::getStatus)
            .containsExactly(source.getStatus());
    }
}
