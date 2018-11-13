package com.cyhee.rabit.web.user;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.cmm.ResponseHelper;
import com.cyhee.rabit.service.follow.FollowService;
import com.cyhee.rabit.service.user.UserService;
import com.cyhee.rabit.service.user.UserStoreService;

@RestController
@RequestMapping("rest/v1/users/{username}")
public class UserStoreController {
	@Autowired
	UserStoreService userStoreService;
	
	@Resource(name="userService")
	UserService userService;
	
	@Autowired
	FollowService followService;
	
	@GetMapping("/goals")
	public ResponseEntity<Page<Goal>> getGoals(@PathVariable String username, Pageable pageable) {
		User author = userService.getUserByUsername(username);
		return new ResponseEntity<>(userStoreService.getGoals(author, ContentStatus.all(), pageable), HttpStatus.OK);
	}

	@GetMapping("/followers")
	public ResponseEntity<Page<User>> getFollowers(@PathVariable String username, Pageable pageable) {
	    User followee = userService.getUserByUsername(username);
	    return new ResponseEntity<>(userStoreService.getFollowers(followee, pageable), HttpStatus.OK);
    }
	
	@PostMapping("/followers")
	public ResponseEntity<Void> addFollowers(@PathVariable String username) {
	    User followee = userService.getUserByUsername(username);
	    User follower = userService.getUserByUsername(AuthHelper.getUsername());
	    
	    Follow follow = new Follow();
	    follow.setFollowee(followee).setFollower(follower);
	    followService.addFollow(follow);
	    return ResponseHelper.createdEntity(ContentType.FOLLOW, follow.getId());
    }

    @GetMapping("/followees")
    public ResponseEntity<Page<User>> getFollowees(@PathVariable String username, Pageable pageable) {
        User follower = userService.getUserByUsername(username);
        return new ResponseEntity<>(userStoreService.getFollowees(follower, pageable), HttpStatus.OK);
    }
}
