package com.cyhee.rabit.web.user;

import javax.annotation.Resource;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.follow.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.user.UserService;
import com.cyhee.rabit.service.user.UserStoreService;

@RestController
@RequestMapping("rest/v1/users/{id}")
public class UserStoreController {
	@Autowired
	UserStoreService userStoreService;
	
	@Resource(name="userService")
	UserService userService;
	
	@GetMapping("/goals")
	public ResponseEntity<Page<Goal>> getGoals(@PathVariable long id, Pageable pageable) {
		User author = userService.getUser(id);
		return new ResponseEntity<>(userStoreService.getGoals(author, ContentStatus.all(), pageable), HttpStatus.OK);
	}

	@GetMapping("/followers")
	public ResponseEntity<Page<Follow>> getFollowers(@PathVariable long id, Pageable pageable) {
	    User followee = userService.getUser(id);
	    return new ResponseEntity<>(userStoreService.getFollowers(followee, pageable), HttpStatus.OK);
    }

    @GetMapping("/followees")
    public ResponseEntity<Page<Follow>> getFollowees(@PathVariable long id, Pageable pageable) {
        User follower = userService.getUser(id);
        return new ResponseEntity<>(userStoreService.getFollowees(follower, pageable), HttpStatus.OK);
    }
}
